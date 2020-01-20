package com.onewisebit.scpescape.fsm

import com.onewisebit.scpescape.model.entities.*
import io.reactivex.Flowable
import io.reactivex.Single

interface GameStateContract {

    interface GameStateView

    interface GameStatePresenter {
        fun getGame(): Game
        fun getParticipants(): Single<List<Participant>>
        fun getPlayers(): Single<List<Player>>
        fun getRounds(): Flowable<List<Round>>
        fun getTurns(): Flowable<List<Turn>>
        fun getMode(): Single<Mode>
        fun assignRoles()
    }

    interface GameStateModel {

        fun getGame(gameID: Long): Single<Game>
        fun getParticipants(gameID: Long): Single<List<Participant>>
        fun getPlayers(gameID: Long): Single<List<Player>>
        fun getRounds(gameID: Long): Flowable<List<Round>>
        fun getTurns(gameID: Long): Flowable<List<Turn>>
        fun getMode(gameID: Long): Single<Mode>
        fun assignRoles(gameID: Long)

    }
}