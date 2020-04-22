package com.onewisebit.scpescape.game.composable.model

import com.onewisebit.scpescape.game.composable.ContractTurn
import com.onewisebit.scpescape.model.entities.Turn
import com.onewisebit.scpescape.model.repositories.InTurnRepository

class ModelTurnImpl(val turnRepository: InTurnRepository) :
    ContractTurn.ModelTurn {
    override suspend fun getTurns(gameID: Long): List<Turn> = turnRepository.getGameTurns(gameID)
    override suspend fun getRoundTurns(gameID: Long, roundNumber: Int): List<Turn>? =
        turnRepository.getRoundTurns(gameID, roundNumber)

    override suspend fun getLatestRoundTurns(gameID: Long): List<Turn>? =
        turnRepository.getLatestRoundTurns(gameID)

    override suspend fun getLatestTurn(gameID: Long): Turn? = turnRepository.getLastTurn(gameID)

    override suspend fun addTurn(gameID: Long, playerId: Long): Int {
        // Note: this is supposing Rounds are always created before Turns
        // this is equivalent to the first turn of a game
        var roundNumber: Int = 0
        var turnNumber: Int = 0

        // get the last turn of the last round
        val lastRoundTurn: Turn? = turnRepository.getLastRoundTurn(gameID)

        // check if we're in a new round
        if (lastRoundTurn == null) {
            //todo: fix error this return null on a new round
            //todo: turns get deleted between a round and the next one
            val lastTurn = turnRepository.getLastTurn(gameID)
            // check if we're in the first turn of a new round (not the first one)
            if (lastTurn != null) {
                roundNumber = lastTurn.roundNumber + 1
                turnNumber = lastTurn.turnNumber + 1
            }
            // we don't use else here to account for the first turn of the game because roundNumber and turnNumber are already zero
        }
        // we're in a new turn of the same round
        else {
            roundNumber = lastRoundTurn.roundNumber
            turnNumber = lastRoundTurn.turnNumber + 1
        }

        turnRepository.insertTurn(
            Turn(
                gameID,
                roundNumber,
                turnNumber,
                playerId
            )
        )

        return turnNumber
    }

    override suspend fun getMissingTurnsParticipants(gameID: Long): List<Long>? =
        turnRepository.getMissingRoundPlayers(gameID)
}