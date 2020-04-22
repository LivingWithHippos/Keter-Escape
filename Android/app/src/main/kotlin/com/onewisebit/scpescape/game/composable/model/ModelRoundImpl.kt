package com.onewisebit.scpescape.game.composable.model

import com.onewisebit.scpescape.game.composable.ContractRound
import com.onewisebit.scpescape.model.entities.Round
import com.onewisebit.scpescape.model.parsed.RoundDetails
import com.onewisebit.scpescape.model.repositories.InRoundRepository

class ModelRoundImpl(val roundRepository: InRoundRepository) :
    ContractRound.ModelRound {
    override suspend fun getRounds(gameID: Long): List<Round> = roundRepository.getRounds(gameID)

    override suspend fun getLastRound(gameID: Long): Round? = roundRepository.getLastRound(gameID)

    override suspend fun getRoundDetail(modeId: Int, roundCode: String): RoundDetails? =
        roundRepository.getRoundInfo(modeId, roundCode)

    override suspend fun getAllModeDetails(modeId: Int): List<RoundDetails>? =
        roundRepository.getAllRoundsDetails(modeId)

    override suspend fun getRoundsMode(gameId: Long): Int = roundRepository.getRoundsMode(gameId)

    override suspend fun addRound(gameID: Long, details: String, roundNumber: Int) {
        var number = roundNumber

        if (number < 0) {
            number = roundRepository.getRoundsNumber(gameID)
            if (number > 0)
                number += 1
        }

        val mode = getRoundsMode(gameID)

        roundRepository.insertRound(Round(number, gameID, mode, details))
    }

    /**
     * Adds a round with the opposite detail of the previous one. Will throw an exception if there are no previous rounds.
     */
    override suspend fun addRound(gameID: Long) {
        val lastRound = roundRepository.getLastRound(gameID)

        if (lastRound == null)
            throw IllegalAccessError("Null round loaded. This probably means this method was used before the creation of the first round.")
        else {
            val details = if (lastRound.details=="lights_out" ) "lights_on" else "lights_out"
            addRound(gameID,details, lastRound.num+1)
        }
    }

    companion object {
        private val TAG = ModelRoundImpl::class.java.simpleName
    }
}