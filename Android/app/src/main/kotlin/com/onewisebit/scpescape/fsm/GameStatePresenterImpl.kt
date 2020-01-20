package com.onewisebit.scpescape.fsm

import android.annotation.SuppressLint
import android.util.Log
import com.onewisebit.scpescape.model.entities.*
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
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

    override fun getMode(): Single<Mode> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun assignRoles() {
        val participants : Single<List<Participant>> = model.getParticipants(gameId)
        val mode : Single<Mode>  = model.getMode(gameId)

        Single.zip<List<Participant>,Mode,Pair<List<Participant>,Mode>>(participants,mode, BiFunction{
            part,mod -> Pair(part,mod)
        })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {

                },
                { Log.d(TAG, "Error while loading players for game $gameId ") }
            )
    }

    companion object {
        private val TAG = GameStatePresenterImpl::class.java.simpleName
    }
}