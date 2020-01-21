package com.onewisebit.scpescape.game.presenter

import android.annotation.SuppressLint
import android.util.Log
import com.onewisebit.scpescape.game.GameStateContract
import com.onewisebit.scpescape.model.ModeDataClass
import com.onewisebit.scpescape.model.RolesDetail
import com.onewisebit.scpescape.model.entities.*
import io.reactivex.Flowable
import io.reactivex.Single

@SuppressLint("CheckResult")
class GameStatePresenterImpl(
    gsView: GameStateContract.GameStateView,
    gsModel: GameStateContract.GameStateModel,
    gameID: Long
) : GameStateContract.GameStatePresenter {

    private var view: GameStateContract.GameStateView = gsView
    private var model: GameStateContract.GameStateModel = gsModel

    private var gameId: Long = gameID

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
        var roles: List<RolesDetail> =
            model.getMode(gameId).rolesDivision.filter { participants.size >= it.minPlayers && participants.size <= it.maxPlayers }[0].roles
        val defaultRole = roles.filter { it.default }[0]
        participants.forEach {

        }
        Log.d(TAG, "Error while loading players for game $gameId ")
    }

    companion object {
        private val TAG = GameStatePresenterImpl::class.java.simpleName
    }
}