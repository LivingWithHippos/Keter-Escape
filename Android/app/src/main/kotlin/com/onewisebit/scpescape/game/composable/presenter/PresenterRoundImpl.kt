package com.onewisebit.scpescape.game.composable.presenter

import com.onewisebit.scpescape.game.composable.ContractRound
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
        return getRoundDetail(round.details)
    }

    override suspend fun getRoundDetail(roundCode: String): RoundDetails? {
        val modeId = modelRound.getRoundsMode(gameID)
        return modelRound.getRoundDetail(modeId, roundCode)
    }

    override suspend fun getAllModeDetails(): List<RoundDetails>? {
        val modeId = modelRound.getRoundsMode(gameID)
        return modelRound.getAllModeDetails(modeId)
    }

    override suspend fun addRound(details: String) = modelRound.addRound(gameID, details)

}