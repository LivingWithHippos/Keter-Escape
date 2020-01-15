package com.onewisebit.scpescape.fsm

import com.onewisebit.scpescape.model.entities.*
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

interface GameStateContract {

    interface GameStateView

    interface GameStatePresenter {
        fun getGame(): Game
        fun getParticipants(): Flowable<List<Participant>>
        fun getPlayers(): Flowable<List<Player>>
        fun getRounds(): Flowable<List<Round>>
        fun getTurns(): Flowable<List<Turn>>
        fun getMode(): Single<Mode>
    }

    interface GameStateModel {

        fun getGame(gameID: Long): Single<Game>
        fun getParticipants(gameID: Long): Flowable<List<Participant>>
        fun getPlayers(gameID: Long): Flowable<List<Player>>
        fun getRounds(gameID: Long): Flowable<List<Round>>
        fun getTurns(gameID: Long): Flowable<List<Turn>>
        fun getMode(gameID: Long): Single<Mode>

    }
}