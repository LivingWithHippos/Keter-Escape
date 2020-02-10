package com.onewisebit.scpescape.model.repositories

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.onewisebit.scpescape.model.parsed.*
import com.onewisebit.scpescape.utilities.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.IllegalArgumentException

class TurnActionRepository(context: Context): JSONRepository(context), InTurnActionRepository {

        override suspend fun getTemplates(): List<TurnAction> {
            val path = TEMPLATE_FOLDER.plus(ACTION_FOLDER)
            val templates : Array<String>? = getFiles(path)
            val actions : MutableList<TurnAction> = mutableListOf()
            if (!templates.isNullOrEmpty()){
                templates.forEach { template ->
                    when(template){
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

        override suspend fun getVote(path: String): List<VoteTurn>? =
            withContext(Dispatchers.IO) {
                try {
                    context.assets.open(path).use { inputStream ->
                        JsonReader(inputStream.reader()).use { jsonReader ->
                            val voteType = object : TypeToken<List<VoteTurn>>() {}.type
                            val votesList: List<VoteTurn> = Gson().fromJson(jsonReader, voteType)

                            votesList
                        }
                    }
                } catch (ex: Exception) {
                    //TODO: remove all of these and either return an empty list or throw an exception
                    Log.e(TAG, "Error reading VoteTurn", ex)
                    null
                }
            }

    override suspend fun getInfo(path: String): List<InfoTurn>? =
        withContext(Dispatchers.IO) {
            try {
                context.assets.open(path).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val infoType = object : TypeToken<List<InfoTurn>>() {}.type
                        val infoList: List<InfoTurn> = Gson().fromJson(jsonReader, infoType)

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
            val actions : MutableList<TurnAction> = mutableListOf()
            var found: Boolean = false
            // check in folders two levels in (first level is the assets folder)
            val modePath : String = getModeFolder(modeId)
                ?: throw IllegalArgumentException("No mode found for mode id $modeId")

            val actionsPath = modePath.plus(ACTION_FOLDER)
            val voteActions = getVote(actionsPath.plus(VOTE_FILE))
            if(voteActions != null)
                actions.addAll(voteActions)
            // we get info actions
            val infoActions = getInfo(actionsPath.plus(INFO_FILE))
            if(infoActions != null)
                actions.addAll(infoActions)

            actions
        }

    override suspend fun getRoleAction(modeId: Int, roleName: String, roundCode: String): TurnAction {
        val actions = getModeActions(modeId)
        val roleDetails = getRoleDetails(modeId)
        val turnActionName = roleDetails.first { role -> role.name == roleName }
            .roundActions
            .first { action -> action.roundCode == roundCode }
            .actionName
        return actions.first { it.name == turnActionName }
    }

    suspend fun getRoleDetails(modeId: Int): List<RoleDetails> =
    withContext(Dispatchers.IO) {

        val roles : MutableList<RoleDetails> = mutableListOf()

        // check in folders two levels in (first level is the assets folder)
        val modePath : String = getModeFolder(modeId)
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

    companion object {
        private val TAG = TurnActionRepository::class.java.simpleName
    }
}