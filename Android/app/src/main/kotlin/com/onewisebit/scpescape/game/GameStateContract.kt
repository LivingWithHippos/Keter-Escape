package com.onewisebit.scpescape.game

import com.onewisebit.scpescape.model.ModeDataClass
import com.onewisebit.scpescape.model.entities.*
import io.reactivex.Flowable
import io.reactivex.Single

interface GameStateContract {

    interface GameStateView

    interface GameStatePresenter {
        fun getGame(): Game
        suspend fun getParticipants(): List<Participant>
        fun getPlayers(): Single<List<Player>>
        fun getRounds(): Flowable<List<Round>>
        fun getTurns(): Flowable<List<Turn>>
        suspend fun getMode(): ModeDataClass
        suspend fun assignRoles()
        fun onDestroy()
        suspend fun getCurrentParticipant(): Participant
        suspend fun hasNextTurnPlayer(): Boolean
    }

    interface GameStateModel {

        fun getGame(gameID: Long): Single<Game>
        suspend fun getParticipants(gameID: Long): List<Participant>
        fun getPlayers(gameID: Long): Single<List<Player>>
        fun getRounds(gameID: Long): Flowable<List<Round>>
        fun getTurns(gameID: Long): Flowable<List<Turn>>
        suspend fun getMode(gameID: Long): ModeDataClass?
        suspend fun assignRole(gameID: Long, playerID: Long, roleName : String)
        suspend fun getCurrentParticipant(gameID: Long): Participant
        suspend fun getMissingRoundParticipants(gameID: Long): List<Participant>

    }
}