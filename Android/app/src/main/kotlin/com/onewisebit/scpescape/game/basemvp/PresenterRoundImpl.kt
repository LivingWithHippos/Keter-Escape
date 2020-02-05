package com.onewisebit.scpescape.game.basemvp

import com.onewisebit.scpescape.model.entities.Round
import com.onewisebit.scpescape.model.parsed.RoundDetails

class PresenterRoundImpl(val modelRound: ContractRound.ModelRound, val gameID: Long) :
    ContractRound.PresenterRound {

    override suspend fun getRounds(): List<Round> = modelRound.getRounds(gameID)

    override suspend fun getCurrentRound(): Round {
        return getRounds().maxBy { it.num }
            ?: throw IllegalStateException("PresenterRound couldn't get last round from game $gameID")
    }

    override suspend fun getCurrentRoundDetails(): RoundDetails? {
        val round = getCurrentRound()
        return getRoundDetail(round.modeID,round.details)
    }

    override suspend fun getRoundDetail(modeId: Int, roundCode: String): RoundDetails? = modelRound.getRoundDetail(modeId, roundCode)

    override suspend fun getAllDetails(modeId: Int): List<RoundDetails>? = modelRound.getAllModeDetails(modeId)

}