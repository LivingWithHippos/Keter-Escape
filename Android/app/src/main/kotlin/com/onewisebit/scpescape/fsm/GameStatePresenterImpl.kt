package com.onewisebit.scpescape.fsm

import android.util.Log
import com.onewisebit.scpescape.model.entities.*
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GameStatePresenterImpl (gsView: GameStateContract.GameStateView,
                              gsModel: GameStateContract.GameStateModel,
                              gameID: Long
): GameStateContract.GameStatePresenter {

    private var view: GameStateContract.GameStateView = gsView
    private var model: GameStateContract.GameStateModel = gsModel

    private var gameId : Long = gameID

    override fun getGame(): Game {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getParticipants(): Flowable<List<Participant>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPlayers(): Flowable<List<Player>> = model.getPlayers(gameId)

    override fun getRounds(): Flowable<List<Round>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTurns(): Flowable<List<Turn>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMode(): Single<Mode> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private val TAG = GameStatePresenterImpl::class.java.simpleName
    }
}