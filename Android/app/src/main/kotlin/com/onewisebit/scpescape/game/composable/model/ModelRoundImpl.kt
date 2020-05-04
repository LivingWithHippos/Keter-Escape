package com.onewisebit.scpescape.game.composable.model

import android.util.Log
import com.onewisebit.scpescape.game.composable.ContractRound
import com.onewisebit.scpescape.model.entities.Round
import com.onewisebit.scpescape.model.parsed.RoundDetails
import com.onewisebit.scpescape.model.repositories.InModeJSONRepository
import com.onewisebit.scpescape.model.repositories.InRoundRepository

class ModelRoundImpl(
    val roundRepository: InRoundRepository,
    val modeRepository: InModeJSONRepository
) :
    ContractRound.ModelRound {
    override suspend fun getRounds(gameID: Long): List<Round> = roundRepository.getRounds(gameID)

    override suspend fun getLastRound(gameID: Long): Round? = roundRepository.getLastRound(gameID)

    override suspend fun getRoundDetail(modeId: Int, roundCode: String): RoundDetails {
        val details = roundRepository.getRoundInfo(modeId, roundCode)
        if (details == null)
            throw IllegalStateException("Loaded null round details from roundRepository for mode $modeId, round $roundCode")
        else
            return details
    }

    override suspend fun getAllModeDetails(modeId: Int): List<RoundDetails>? =
        roundRepository.getAllRoundsDetails(modeId)

    override suspend fun getRoundsMode(gameId: Long): Int = roundRepository.getRoundsMode(gameId)

    override suspend fun setRoundReplay(gameId: Long, roundNumber: Int, replay: Boolean) =
        roundRepository.setRoundReplayable(gameId, roundNumber, replay)

    override suspend fun getRoundReplay(gameID: Long, num: Int): Boolean =
        roundRepository.getRoundReplayable(gameID, num)

    override suspend fun addRound(
        gameID: Long,
        details: String,
        roundNumber: Int,
        replay: Boolean
    ) {
        var number: Int = roundNumber
        if (number < 0) {
            // if roundNumber is less than zero we check how many rows are in the Round table (hehe) for this game
            //todo: rename method to getRoundsRows?
            number = roundRepository.getRoundsNumber(gameID)
            // since getRoundsNumber returns the number of rows we can use that number directly
            // 0 rows = start of the game, insert zero for the first round
            // 1 row = second round, insert 1 as round number
            // 2 rows = third round, insert 2 as round number
            // etc.
        }

        val mode = getRoundsMode(gameID)

        val row = roundRepository.insertRound(Round(number, gameID, mode, details, replay))
        Log.d(TAG, "Inserted Round's row is $row")
    }

    /**
     * Adds a round with the opposite detail of the previous one. Will throw an exception if there are no previous rounds.
     */
    override suspend fun addRound(gameID: Long) {
        val lastRound = roundRepository.getLastRound(gameID)
        val mode = modeRepository.getGameMode(gameID)

        if (mode == null)
            throw IllegalAccessError("Null mode ($mode) loaded in addRound")
        else {
            val roundSequence = mode.roundsSequence
            // if we're in the first round
            var newRoundModeIndex = 0
            var newRoundNumber = 0
            // are we in a round after the first?
            lastRound?.let { round ->
                // get the last round mode
                val modeIndex = roundSequence.indexOf(round.details)
                if (modeIndex < 0)
                    throw IllegalArgumentException("Round type not found for ${lastRound.details} in sequence $roundSequence")
                else
                // get the next round mode from the round sequence
                {
                    // does the last round type need to be repeated (ties, etc)?
                    if (round.replay == true) {
                        newRoundModeIndex = modeIndex
                        roundRepository.setRoundReplayable(gameID, round.num, false)
                    } else
                        newRoundModeIndex = (modeIndex + 1) % roundSequence.size
                }
                //if the round is replayed it needs a bigger number anyway
                newRoundNumber = round.num + 1
            }

            addRound(gameID, roundSequence[newRoundModeIndex], newRoundNumber)
        }
    }

    companion object {
        private val TAG = ModelRoundImpl::class.java.simpleName
    }
}