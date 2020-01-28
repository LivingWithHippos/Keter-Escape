package com.onewisebit.scpescape.game.presenter

import android.annotation.SuppressLint
import com.onewisebit.scpescape.game.GameStateContract
import com.onewisebit.scpescape.model.ModeDataClass
import com.onewisebit.scpescape.model.RolesDetail
import com.onewisebit.scpescape.model.entities.*
import io.reactivex.Flowable
import io.reactivex.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.random.Random

@SuppressLint("CheckResult")
class GameStatePresenterImpl(
    gsView: GameStateContract.GameStateView,
    gsModel: GameStateContract.GameStateModel,
    gameID: Long
) : GameStateContract.GameStatePresenter {

    private var view: GameStateContract.GameStateView = gsView
    private var model: GameStateContract.GameStateModel = gsModel

    private var gameId: Long = gameID


    private val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun getGame(): Game {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getParticipants(): List<Participant> = model.getParticipants(gameId)

    override fun getPlayers(): Single<List<Player>> = model.getPlayers(gameId)

    override fun getRounds(): Flowable<List<Round>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTurns(): Flowable<List<Turn>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getMode(): ModeDataClass {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun assignRoles() {
        //TODO: if it's a game in progress this doesn't need to be called or we can check from here directly
        val participants: List<Participant> = model.getParticipants(gameId)
        val mode: ModeDataClass? = model.getMode(gameId)
        if (mode != null) {
            // get roles division for this mode
            val roles: List<RolesDetail> =
                mode.rolesDivision.filter { participants.size >= it.minPlayers && participants.size <= it.maxPlayers }[0].roles
            // for simplicity we build a string array with every necessary role
            val possibleRoles: ArrayList<String> = ArrayList()
            roles.filter{!it.default}.forEach{ details ->
                for (i in 0 until details.quantity)
                    possibleRoles.add(details.role)
            }
            // special role are fixed for the players range (for now)
            // the rest of the participants will have the default role
            val defaultRole = roles.filter { it.default }[0]
            while (possibleRoles.size < participants.size)
                possibleRoles.add(defaultRole.role)

            // assigning the various roles in the db
            participants.forEach {
                val roleName = possibleRoles.removeAt(Random.nextInt(possibleRoles.size))
                model.assignRole(gameId, it.playerID, roleName)
            }

        }
        else
            throw IllegalArgumentException("Error while loading mode for game $gameId")
    }

    override fun onDestroy() {
        job.cancel()
    }

    override suspend fun getCurrentParticipant(): Participant = model.getCurrentParticipant(gameId)

    override suspend fun hasNextTurnPlayer(): Boolean {
        return model.getMissingRoundParticipants(gameId).isNotEmpty()
    }

    companion object {
        private val TAG = GameStatePresenterImpl::class.java.simpleName
    }
}