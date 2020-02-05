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
import com.onewisebit.scpescape.utilities.ROUND_DETAILS_FILENAME
import io.reactivex.Completable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoundRepository(private val roundDAO: RoundDAO, private val gameDAO: GameDAO, private val context: Context) : InRoundRepository {

    override fun insertRound(round: Round): Completable = roundDAO.insertRound(round)

    override suspend fun getRounds(gameID: Long): List<Round> = roundDAO.getRounds(gameID)

    override fun deleteGameRounds(gameID: Long): Completable = roundDAO.deleteGameRounds(gameID)

    override suspend fun getGameRoundInfo(
        gameId: Long,
        roundCode: String
    ): RoundDetails? {
        return getAllGameRoundDetails(gameId)?.firstOrNull{ it.code == roundCode }
    }

    override suspend fun getRoundInfo(modeId: Int, roundCode: String): RoundDetails? {
        return getAllModeRoundInfo(modeId)?.firstOrNull { it.code == roundCode }
    }

    override suspend fun getAllGameRoundDetails(
        gameId: Long
    ): List<RoundDetails>? {
        val gameMode = gameDAO.getModeID(gameId)
        return getAllModeRoundInfo(gameMode)
    }

    override suspend fun getAllModeRoundInfo(modeId: Int): List<RoundDetails>? {
        return getAllRoundsDetails()?.filter { it.mode == modeId }
    }

    override suspend fun getAllRoundsDetails(): List<RoundDetails>? =
        withContext(Dispatchers.IO) {
            try {
                // retrieving round details
                context.assets.open(ROUND_DETAILS_FILENAME).use { inputStream ->
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


    companion object {
        private val TAG = RoundRepository::class.java.simpleName
    }
}