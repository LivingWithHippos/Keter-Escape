package com.onewisebit.scpescape.game.basemvp

import com.onewisebit.scpescape.model.entities.Turn
import com.onewisebit.scpescape.model.repositories.InTurnRepository

class ModelTurnImpl(val turnRepository: InTurnRepository) : ContractTurn.ModelTurn {
    override suspend fun getTurns(gameID: Long): List<Turn> = turnRepository.getGameTurns(gameID)
    override suspend fun getRoundTurns(gameID: Long, roundNumber: Int): List<Turn> =
        turnRepository.getRoundTurns(gameID, roundNumber)
}