package com.onewisebit.scpescape.game.composable.presenter

import com.onewisebit.scpescape.game.composable.ContractRound
import com.onewisebit.scpescape.model.entities.Round
import com.onewisebit.scpescape.model.parsed.RoundDetails

class PresenterRoundImpl(val modelRound: ContractRound.ModelRound, val gameID: Long) :
    ContractRound.PresenterRound {

    override suspend fun getRounds(): List<Round> = modelRound.getRounds(gameID)

    override suspend fun getCurrentRound(): Round? =
        modelRound.getLastRound(gameID)


    override suspend fun getCurrentRoundDetails(): RoundDetails {
        val round = getCurrentRound()
        if (round == null)
            throw IllegalStateException("Obtained a null round in getCurrentRoundDetails.")
        else
            return getRoundDetail(round.details)
    }
    //todo: rename details and code here to match
    override suspend fun getRoundDetail(roundCode: String): RoundDetails {
        val modeId = modelRound.getRoundsMode(gameID)
        return modelRound.getRoundDetail(modeId, roundCode)
    }

    override suspend fun getAllModeDetails(): List<RoundDetails>? {
        val modeId = modelRound.getRoundsMode(gameID)
        return modelRound.getAllModeDetails(modeId)
    }

    override suspend fun addRound() =
        modelRound.addRound(gameID)

}