package com.onewisebit.scpescape.game.composable.presenter

import com.onewisebit.scpescape.game.composable.ContractParticipant
import com.onewisebit.scpescape.game.composable.ContractTurn
import com.onewisebit.scpescape.model.entities.Turn

class PresenterTurnImpl(
    val modelTurn: ContractTurn.ModelTurn,
    val modelParticipant: ContractParticipant.ModelParticipant,
    val gameID: Long
) : ContractTurn.PresenterTurn {
    override suspend fun getTurns(): List<Turn> = modelTurn.getTurns(gameID)

    override suspend fun getRoundTurns(roundNumber: Int): List<Turn>? =
        modelTurn.getRoundTurns(gameID, roundNumber)

    override suspend fun getLatestRoundTurns(): List<Turn>? = modelTurn.getLatestRoundTurns(gameID)

    override suspend fun getLatestTurn(): Turn {
        return modelTurn.getLatestTurn(gameID)
            ?: throw IllegalArgumentException("No turns found for game $gameID or error loading turns.")
    }

    override suspend fun isLastTurn(): Boolean =
        modelParticipant.getMissingRoundParticipants(gameID).isEmpty()

    override suspend fun addTurn(playerId: Long): Int {
        return modelTurn.addTurn(gameID, playerId)
    }
}