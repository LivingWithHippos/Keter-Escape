package com.onewisebit.scpescape.model.repositories

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.onewisebit.scpescape.model.parsed.RoundInformation
import com.onewisebit.scpescape.model.parsed.RoundDetails
import com.onewisebit.scpescape.utilities.ROUND_DETAILS_FILENAME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoundDetailsRepositoryImpl(private val context: Context) : RoundDetailRepository {

    override suspend fun getRoundDetail(modeId: Int, roundCode: String): RoundInformation? {
        return getAllDetails(modeId)?.firstOrNull { it.code == roundCode }
    }

    override suspend fun getAllDetails(modeId: Int): List<RoundInformation>? {
        return getAllRoundsDetails()?.first { it.modeId == modeId }?.details
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
        private val TAG = RoundDetailsRepositoryImpl::class.java.simpleName
    }
}