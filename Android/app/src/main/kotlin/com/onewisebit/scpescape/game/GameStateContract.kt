package com.onewisebit.scpescape.game

import com.onewisebit.scpescape.model.ModeDataClass
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
        suspend fun getMode(): ModeDataClass
        suspend fun assignRoles()
    }

    interface GameStateModel {

        fun getGame(gameID: Long): Single<Game>
        suspend fun getParticipants(gameID: Long): List<Participant>
        fun getPlayers(gameID: Long): Single<List<Player>>
        fun getRounds(gameID: Long): Flowable<List<Round>>
        fun getTurns(gameID: Long): Flowable<List<Turn>>
        suspend fun getMode(gameID: Long): ModeDataClass
        fun assignRoles(gameID: Long)

    }
}