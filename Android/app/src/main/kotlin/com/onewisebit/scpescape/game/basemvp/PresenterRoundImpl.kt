package com.onewisebit.scpescape.game.basemvp

import com.onewisebit.scpescape.model.entities.Round

class PresenterRoundImpl(val modelRound: ContractRound.ModelRound, val gameID: Long) :
    ContractRound.PresenterRound {

    override suspend fun getRounds(): List<Round> = modelRound.getRounds(gameID)

    override suspend fun getCurrentRound(): Round {
        return getRounds().maxBy { it.num }
            ?: throw IllegalStateException("PresenterRound couldn't get last round from game $gameID")
    }
}