package com.onewisebit.scpescape.game.composable.model

import com.onewisebit.scpescape.game.composable.ContractRound
import com.onewisebit.scpescape.model.entities.Round
import com.onewisebit.scpescape.model.parsed.RoundDetails
import com.onewisebit.scpescape.model.repositories.InRoundRepository

class ModelRoundImpl(val roundRepository: InRoundRepository) :
    ContractRound.ModelRound {
    override suspend fun getRounds(gameID: Long): List<Round> = roundRepository.getRounds(gameID)

    override suspend fun getRoundDetail(modeId: Int, roundCode: String): RoundDetails? =
        roundRepository.getRoundInfo(modeId, roundCode)

    override suspend fun getAllModeDetails(modeId: Int): List<RoundDetails>? =
        roundRepository.getAllRoundsDetails(modeId)

    override suspend fun getRoundsMode(gameId: Long): Int = roundRepository.getRoundsMode(gameId)

    override suspend fun addRound(gameID: Long, details: String) {
        var roundsNumber = roundRepository.getRoundsNumber(gameID)

        if (roundsNumber > 0)
            roundsNumber += 1

        val mode = getRoundsMode(gameID)

        roundRepository.insertRound(Round(roundsNumber, gameID, mode, details))
    }
}