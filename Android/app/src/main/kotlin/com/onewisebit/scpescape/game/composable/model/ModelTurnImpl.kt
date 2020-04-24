package com.onewisebit.scpescape.game.composable.model

import com.onewisebit.scpescape.game.composable.ContractTurn
import com.onewisebit.scpescape.model.entities.Round
import com.onewisebit.scpescape.model.entities.Turn
import com.onewisebit.scpescape.model.repositories.InRoundRepository
import com.onewisebit.scpescape.model.repositories.InTurnRepository

class ModelTurnImpl(val turnRepository: InTurnRepository, val roundRepository: InRoundRepository) :
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

        // get the last played turn
        val lastPlayedTurn: Turn? = turnRepository.getLastTurn(gameID)
        // get the last created round
        //todo: check if rounds are always created before turns (should be)
        val lastRound: Round? = roundRepository.getLastRound(gameID)

        if (lastPlayedTurn != null)
            turnNumber = lastPlayedTurn.turnNumber + 1

        if (lastRound != null)
            roundNumber = lastRound.num
        else
            throw IllegalArgumentException("Failed to load last Round")


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