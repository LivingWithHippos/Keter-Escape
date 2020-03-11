package com.onewisebit.scpescape.model.repositories

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.onewisebit.scpescape.model.daos.GameDAO
import com.onewisebit.scpescape.model.daos.RoundDAO
import com.onewisebit.scpescape.model.entities.Round
import com.onewisebit.scpescape.model.parsed.RoundDetails
import com.onewisebit.scpescape.utilities.ROUND_FILE
import com.onewisebit.scpescape.utilities.ROUND_FOLDER
import io.reactivex.Completable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoundRepository(
    private val roundDAO: RoundDAO,
    private val gameDAO: GameDAO,
    context: Context
) : JSONRepository(context), InRoundRepository {

    override suspend fun insertRound(round: Round) = roundDAO.insertRound(round)

    override suspend fun getRounds(gameID: Long): List<Round> = roundDAO.getRounds(gameID)

    override suspend fun getLastRound(gameID: Long): Round? = roundDAO.getCurrentRound(gameID)

    override fun deleteGameRounds(gameID: Long): Completable = roundDAO.deleteGameRounds(gameID)

    override suspend fun getRoundsMode(gameID: Long): Int = roundDAO.getRoundMode(gameID)

    override suspend fun getGameRoundInfo(
        gameId: Long,
        roundCode: String
    ): RoundDetails? {
        return getAllGameRoundDetails(gameId)?.firstOrNull { it.code == roundCode }
    }

    override suspend fun getRoundInfo(modeId: Int, roundCode: String): RoundDetails? {
        return getAllRoundsDetails(modeId)?.firstOrNull { it.code == roundCode }
    }

    override suspend fun getAllGameRoundDetails(
        gameId: Long
    ): List<RoundDetails>? {
        val gameMode = gameDAO.getModeID(gameId)
        return getAllRoundsDetails(gameMode)
    }

    override suspend fun getAllRoundsDetails(modeId: Int): List<RoundDetails>? =
        withContext(Dispatchers.IO) {
            val modePath: String = getModeFolder(modeId)
                ?: throw IllegalArgumentException("No mode found for mode id $modeId")

            val roundPath: String = modePath.plus(ROUND_FOLDER).plus(ROUND_FILE)

            try {
                // retrieving round details
                context.assets.open(roundPath).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val roundType = object : TypeToken<List<RoundDetails>>() {}.type
                        val roundList: List<RoundDetails> = Gson().fromJson(jsonReader, roundType)

                        roundList
                    }
                }
            } catch (ex: Exception) {
                Log.e(TAG, "Error reading round details", ex)
                null
            }
        }

    /**
     * returns the number of turns in the table or zero if there are none.
     */
    override suspend fun getRoundsNumber(gameId: Long): Int = roundDAO.getRoundsNumber(gameId)

    companion object {
        private val TAG = RoundRepository::class.java.simpleName
    }
}