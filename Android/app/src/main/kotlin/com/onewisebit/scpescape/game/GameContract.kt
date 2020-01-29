package com.onewisebit.scpescape.game

import com.onewisebit.scpescape.model.ModeDataClass
import com.onewisebit.scpescape.model.entities.*

interface GameContract {

    interface GameView

    interface GamePresenter {
        suspend fun getGame(): Game
        suspend fun getParticipants(): List<Participant>
        suspend fun getAliveParticipants(): List<Participant>
        suspend fun getCurrentParticipant(): Participant
        suspend fun getPlayers(): List<Player>
        suspend fun getRounds(): List<Round>
        suspend fun getCurrentRound(): Round
        suspend fun getTurns(): List<Turn>
        suspend fun getRoundTurns(roundNumber: Int): List<Turn>
        suspend fun getMode(): ModeDataClass
        suspend fun isLastTurn(): Boolean
        fun onDestroy()
    }

    interface GameModel{

        suspend fun getGame(gameID: Long): Game
        suspend fun getParticipants(gameID: Long): List<Participant>
        suspend fun getPlayers(gameID: Long): List<Player>
        suspend fun getRounds(gameID: Long): List<Round>
        suspend fun getTurns(gameID: Long): List<Turn>
        suspend fun getMode(gameID: Long): ModeDataClass?
        suspend fun getCurrentParticipant(gameID: Long): Participant
        suspend fun getMissingRoundParticipants(gameID: Long): List<Participant>

    }
}