package com.onewisebit.scpescape.game.basemvp

import com.onewisebit.scpescape.model.entities.*
import com.onewisebit.scpescape.model.parsed.ModeDataClass

interface ContractGame {

    interface ViewGame

    interface PresenterGame {
        suspend fun getGame(): Game
    }

    interface ModelGame {
        suspend fun getGame(gameID: Long): Game
    }
}

interface ContractParticipant {

    interface PresenterParticipant {
        suspend fun getParticipants(): List<Participant>
        suspend fun getAliveParticipants(): List<Participant>
        suspend fun getCurrentParticipant(): Participant
    }

    interface ModelParticipant {
        suspend fun getParticipants(gameID: Long): List<Participant>
        suspend fun getCurrentParticipant(gameID: Long): Participant
        suspend fun getMissingRoundParticipants(gameID: Long): List<Participant>
    }
}

interface ContractPlayer {

    interface PresenterPlayer {
        suspend fun getPlayers(): List<Player>
    }

    interface ModelPlayer {
        suspend fun getPlayers(gameID: Long): List<Player>
    }
}

interface ContractRound {

    interface PresenterRound {
        suspend fun getRounds(): List<Round>
        suspend fun getCurrentRound(): Round
    }

    interface ModelRound {
        suspend fun getRounds(gameID: Long): List<Round>
    }
}

interface ContractTurn {

    interface PresenterTurn {
        suspend fun getTurns(): List<Turn>
        suspend fun getRoundTurns(roundNumber: Int): List<Turn>
        suspend fun isLastTurn(): Boolean
    }

    interface ModelTurn {
        suspend fun getTurns(gameID: Long): List<Turn>
        suspend fun getRoundTurns(gameID: Long, roundNumber: Int): List<Turn>
    }
}

interface ContractMode {
    interface PresenterMode {
        suspend fun getMode(): ModeDataClass
    }

    interface ModelMode {
        suspend fun getMode(gameID: Long): ModeDataClass?
    }
}