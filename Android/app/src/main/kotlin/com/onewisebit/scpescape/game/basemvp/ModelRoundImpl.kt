package com.onewisebit.scpescape.game.basemvp

import com.onewisebit.scpescape.model.entities.Round
import com.onewisebit.scpescape.model.repositories.InRoundRepository

class ModelRoundImpl(val roundRepository: InRoundRepository): ContractRound.ModelRound {
    override suspend fun getRounds(gameID: Long): List<Round> = roundRepository.getRounds(gameID)
}