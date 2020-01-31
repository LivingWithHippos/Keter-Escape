package com.onewisebit.scpescape.game.basemvp

import com.onewisebit.scpescape.model.entities.Turn

class PresenterTurnImpl(
    val modelTurn: ContractTurn.ModelTurn,
    val modelParticipant: ContractParticipant.ModelParticipant,
    val gameID: Long
) : ContractTurn.PresenterTurn {
    override suspend fun getTurns(): List<Turn> = modelTurn.getTurns(gameID)

    override suspend fun getRoundTurns(roundNumber: Int): List<Turn> =
        modelTurn.getRoundTurns(gameID, roundNumber)

    override suspend fun isLastTurn(): Boolean =
        modelParticipant.getMissingRoundParticipants(gameID).isEmpty()
}