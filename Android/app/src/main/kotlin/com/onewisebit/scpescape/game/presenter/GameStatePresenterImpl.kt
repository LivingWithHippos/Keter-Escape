package com.onewisebit.scpescape.game.presenter

import android.annotation.SuppressLint
import android.util.Log
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

    override fun getParticipants(): Single<List<Participant>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

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
            Log.d(TAG, "Error while loading players for game $gameId")
    }

    override fun onDestroy() {
        job.cancel()
    }

    companion object {
        private val TAG = GameStatePresenterImpl::class.java.simpleName
    }
}