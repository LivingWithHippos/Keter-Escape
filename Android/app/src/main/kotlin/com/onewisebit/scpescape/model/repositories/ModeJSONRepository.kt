package com.onewisebit.scpescape.model.repositories

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.onewisebit.scpescape.model.parsed.ModeDataClass
import com.onewisebit.scpescape.utilities.MODE_DATA_FILENAME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ModeJSONRepository(private val context: Context) : InModeJSONRepository {

    override suspend fun getAllModes(): List<ModeDataClass>? =
        withContext(Dispatchers.IO) {
            try {
                // retrieving modes
                context.assets.open(MODE_DATA_FILENAME).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val modeType = object : TypeToken<List<ModeDataClass>>() {}.type
                        val modesList: List<ModeDataClass> = Gson().fromJson(jsonReader, modeType)

                        modesList
                    }
                }
            } catch (ex: Exception) {
                Log.e(TAG, "Error reading modes", ex)
                null
            }
        }

    override suspend fun getMode(id: Int): ModeDataClass? {
        return getAllModes()?.firstOrNull { it.id == id }
    }

    companion object {
        private val TAG = ModeJSONRepository::class.java.simpleName
    }
}