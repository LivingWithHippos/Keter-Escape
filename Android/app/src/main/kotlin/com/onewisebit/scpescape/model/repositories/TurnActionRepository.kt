package com.onewisebit.scpescape.model.repositories

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.onewisebit.scpescape.model.daos.ModeDAO
import com.onewisebit.scpescape.model.parsed.InfoSettings
import com.onewisebit.scpescape.model.parsed.RoleDetails
import com.onewisebit.scpescape.model.parsed.TurnAction
import com.onewisebit.scpescape.model.parsed.VoteSettings
import com.onewisebit.scpescape.utilities.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TurnActionRepository(context: Context, private val modeDAO: ModeDAO) :
    JSONRepository(context), InTurnActionRepository {

    override suspend fun getTemplates(): List<TurnAction> {
        val path = TEMPLATE_FOLDER.plus(ACTION_FOLDER)
        val templates: Array<String>? = getFiles(path)
        val actions: MutableList<TurnAction> = mutableListOf()
        if (!templates.isNullOrEmpty()) {
            templates.forEach { template ->
                when (template) {
                    TEMPLATE_FILE_VOTE -> {
                        val votes = getVote(path.plus(TEMPLATE_FILE_VOTE))?.map { it as TurnAction }
                        if (!votes.isNullOrEmpty())
                            actions.addAll(votes)
                    }
                    TEMPLATE_FILE_INFO -> {
                        val votes = getInfo(path.plus(TEMPLATE_FILE_INFO))?.map { it as TurnAction }
                        if (!votes.isNullOrEmpty())
                            actions.addAll(votes)
                    }
                }
            }
        }
        return actions
    }

    override suspend fun getVote(path: String): List<VoteSettings>? =
        withContext(Dispatchers.IO) {
            try {
                context.assets.open(path).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val voteType = object : TypeToken<List<VoteSettings>>() {}.type
                        val votesList: List<VoteSettings> = Gson().fromJson(jsonReader, voteType)

                        votesList
                    }
                }
            } catch (ex: Exception) {
                //TODO: remove all of these and either return an empty list or throw an exception
                Log.e(TAG, "Error reading VoteTurn", ex)
                null
            }
        }

    override suspend fun getInfo(path: String): List<InfoSettings>? =
        withContext(Dispatchers.IO) {
            try {
                context.assets.open(path).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val infoType = object : TypeToken<List<InfoSettings>>() {}.type
                        val infoList: List<InfoSettings> = Gson().fromJson(jsonReader, infoType)

                        infoList
                    }
                }
            } catch (ex: Exception) {
                Log.e(TAG, "Error reading VoteTurn", ex)
                null
            }
        }

    override suspend fun getModeActions(modeId: Int): List<TurnAction> =
        withContext(Dispatchers.IO) {
            val actions: MutableList<TurnAction> = mutableListOf()
            // check in folders two levels in (first level is the assets folder)
            val modePath: String = getModeFolder(modeId)
                ?: throw IllegalArgumentException("No mode found for mode id $modeId")

            val actionsPath = modePath.plus(ACTION_FOLDER)
            val voteActions = getVote(actionsPath.plus(VOTE_FILE))
            if (voteActions != null)
                actions.addAll(voteActions)
            // we get info actions
            val infoActions = getInfo(actionsPath.plus(INFO_FILE))
            if (infoActions != null)
                actions.addAll(infoActions)

            actions
        }


    override suspend fun getCompleteModeActions(modeId: Int): List<TurnAction> {
        val partialActions = getModeActions(modeId)
        val actions: MutableList<TurnAction> = mutableListOf()
        partialActions.forEach {
            actions.add(mergeAction(modeId, it))
        }
        return actions
    }

    override suspend fun getPartialAction(
        modeId: Int,
        roleName: String,
        roundCode: String
    ): TurnAction {
        val actions = getModeActions(modeId)
        val roleDetails = getRoleDetails(modeId)
        val turnActionName = roleDetails.first { role -> role.name == roleName }
            .roundActions
            .first { action -> action.roundCode == roundCode }
            .actionName
        return actions.first { it.name == turnActionName }
    }

    override suspend fun getCompleteAction(
        modeId: Int,
        roleName: String,
        roundCode: String
    ): TurnAction {
        val action = getPartialAction(modeId, roleName, roundCode)
        return mergeAction(modeId, action)
    }

    override suspend fun getRoleDetails(modeId: Int): List<RoleDetails> =
        withContext(Dispatchers.IO) {

            val roles: MutableList<RoleDetails> = mutableListOf()

            // check in folders two levels in (first level is the assets folder)
            val modePath: String = getModeFolder(modeId)
                ?: throw IllegalArgumentException("No mode found for mode id $modeId")

            val rolePath = modePath.plus(ROLE_FOLDER).plus(ROLE_FILE)

            try {
                context.assets.open(rolePath).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        // load the current mode.json
                        val roleType = object : TypeToken<List<RoleDetails>>() {}.type
                        roles.addAll(Gson().fromJson(jsonReader, roleType))

                    }
                }
            } catch (ex: Exception) {
                Log.e(TAG, "Error reading file in path $rolePath", ex)
            }

            roles
        }


    override suspend fun getMode(gameId: Long): Int = modeDAO.getGameModeId(gameId)

    private suspend fun mergeAction(modeId: Int, action: TurnAction): TurnAction {

        // the merged action to be returned
        val mergedAction: TurnAction
        // the map to quickly get every action by its name
        val actionMap: HashMap<String, TurnAction> = hashMapOf()
        // a stack to put the actions in the correct merging order.
        val mergingStack: MutableList<String> = mutableListOf()

        // populating the actions map with the templates and a mode actions
        val templates = getTemplates()
        templates.forEach { actionMap[it.name] = it }
        getModeActions(modeId).forEach { actionMap[it.name] = it }

        mergingStack.add(action.name)

        var currentAction: TurnAction = action

        // checking if the current action is a template one. Works because custom actions should not have the same name as template ones.
        while (!templates.map { it.name }.contains(currentAction.name)) {
            // since the map is manually generated we can use !!
            currentAction = actionMap[currentAction.extends]!!
            // we start inserting the action name from the current action and end with the template action
            mergingStack.add(currentAction.name)
        }

        // there is at least the action we put in so we use !!
        mergedAction = actionMap[mergingStack.pop()]!!
        // for every action name inserted, from the last (template) to the first (our action), we launch a merge
        while (mergingStack.isNotEmpty()) {
            //todo: check if we should make merge() return the a new object instead of editing it in place. Look at kotlin data classes copy()
            mergedAction.merge(actionMap[mergingStack.pop()]!!)
        }

        return mergedAction
    }

    companion object {
        private val TAG = TurnActionRepository::class.java.simpleName
    }
}