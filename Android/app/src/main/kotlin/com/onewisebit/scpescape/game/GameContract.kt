package com.onewisebit.scpescape.game

import com.onewisebit.scpescape.model.entities.*
import com.onewisebit.scpescape.model.parsed.ModeDataClass

/**
 * Generic contract with all the game data needed, is implemented by the game single contract
 * TODO: find a way to make implementable only part of it because it is mostly overkill, see composition vs implementation
 */
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
        suspend fun isLastTurn(): Boolean
        suspend fun getMode(): ModeDataClass
        fun onDestroy()
    }

    interface GameModel {

        suspend fun getGame(gameID: Long): Game
        suspend fun getParticipants(gameID: Long): List<Participant>
        suspend fun getCurrentParticipant(gameID: Long): Participant
        suspend fun getMissingRoundParticipants(gameID: Long): List<Participant>
        suspend fun getPlayers(gameID: Long): List<Player>
        suspend fun getRounds(gameID: Long): List<Round>
        suspend fun getTurns(gameID: Long): List<Turn>
        suspend fun getMode(gameID: Long): ModeDataClass?

    }
}