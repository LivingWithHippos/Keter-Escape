package com.onewisebit.scpescape.model.repositories

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.onewisebit.scpescape.model.daos.GameDAO
import com.onewisebit.scpescape.model.parsed.ModeDataClass
import com.onewisebit.scpescape.utilities.MODE_FILE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ModeJSONRepository(context: Context, private val gameDAO: GameDAO) : JSONRepository(context),
    InModeJSONRepository {

    override suspend fun getAllModes(): List<ModeDataClass>? =
        withContext(Dispatchers.IO) {
            val modePaths: MutableList<String> = searchFile(MODE_FILE, maxDepth = 2)
            val modesList: MutableList<ModeDataClass> = mutableListOf()

            for (path in modePaths) {

                try {
                    // retrieving modes
                    context.assets.open(path).use { inputStream ->
                        JsonReader(inputStream.reader()).use { jsonReader ->
                            val modeType = object : TypeToken<List<ModeDataClass>>() {}.type
                            // we need to use a variable here otherwise the boolean from addAll will be returned
                            val added = modesList.addAll(Gson().fromJson(jsonReader, modeType))
                        }
                    }
                } catch (ex: Exception) {
                    Log.e(TAG, "Error reading modes", ex)
                }
            }
            modesList
        }

    override suspend fun getMode(id: Int): ModeDataClass? {
        return getAllModes()?.firstOrNull { it.id == id }
    }

    override suspend fun getGameMode(gameID: Long): ModeDataClass? {
        val modeID = gameDAO.getGameByIdBlocking(gameID).modeID
        return getMode(modeID)
    }

    companion object {
        private val TAG = ModeJSONRepository::class.java.simpleName
    }
}