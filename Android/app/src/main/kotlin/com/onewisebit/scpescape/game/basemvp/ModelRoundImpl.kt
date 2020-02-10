package com.onewisebit.scpescape.game.basemvp

import com.onewisebit.scpescape.model.entities.Round
import com.onewisebit.scpescape.model.parsed.RoundDetails
import com.onewisebit.scpescape.model.repositories.InRoundRepository

class ModelRoundImpl(val roundRepository: InRoundRepository) : ContractRound.ModelRound {
    override suspend fun getRounds(gameID: Long): List<Round> = roundRepository.getRounds(gameID)

    override suspend fun getRoundDetail(modeId: Int, roundCode: String): RoundDetails? =
        roundRepository.getRoundInfo(modeId, roundCode)

    override suspend fun getAllModeDetails(modeId: Int): List<RoundDetails>? =
        roundRepository.getAllRoundsDetails(modeId)

    override suspend fun getRoundsMode(gameId: Long): Int = roundRepository.getRoundsMode(gameId)

    override suspend fun addRound(gameID: Long, details: String) {
        val roundsNumber = roundRepository.getRoundsNumber(gameID).run {
            if (this == 0)
                0
            else
                this + 1
        }

        roundRepository.insertRound(Round(roundsNumber,gameID,getRoundsMode(gameID),details))
    }
}