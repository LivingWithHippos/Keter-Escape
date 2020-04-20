package com.onewisebit.scpescape.model.repositories

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.onewisebit.scpescape.model.parsed.VictoryCondition
import com.onewisebit.scpescape.utilities.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VictoryConditionsRepository(context: Context) : JSONRepository(context),
    InVictoryConditionsRepository {
    override suspend fun getVictoryConditions(modeID: Int): List<VictoryCondition> =
        withContext(Dispatchers.IO) {
            var victories: MutableList<VictoryCondition> = mutableListOf()

            val path = getModeFolder(modeID)
            path?.let { modePath ->
                val victoryConditionPath = modePath.plus(VICTORY_FOLDER).plus(VICTORY_FILE)

                try {
                    context.assets.open(victoryConditionPath).use { inputStream ->
                        JsonReader(inputStream.reader()).use { jsonReader ->
                            val victoryType =
                                object : TypeToken<List<VictoryCondition>>() {}.type
                            val adedd =
                                victories.addAll(Gson().fromJson(jsonReader, victoryType))
                        }
                    }
                } catch (ex: Exception) {
                    Log.e(TAG, "Error reading modes", ex)
                }
            }

            victories
        }

    companion object {
        private val TAG = VictoryConditionsRepository::class.java.simpleName
    }

}