package com.onewisebit.scpescape.model.repositories

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.onewisebit.scpescape.model.ModeFromJson
import com.onewisebit.scpescape.utilities.MODE_DATA_FILENAME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ModeNewRepository(private val context: Context): InModelNewRepository {

    override suspend fun getMode(id: Int): ModeFromJson? =
    withContext(Dispatchers.IO) {
        try {
            // Populating roles
            context.assets.open(MODE_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val modeType = object : TypeToken<List<ModeFromJson>>() {}.type
                    val modesList: List<ModeFromJson> = Gson().fromJson(jsonReader, modeType)

                    modesList[id]
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error reading modes", ex)
            // is there a better way to handle this?
            // if we can't read the file null seems a good return value
            null
        }
    }

    override suspend fun getAllModes(): List<ModeFromJson>? =
        withContext(Dispatchers.IO) {
            try {
                // Populating roles
                context.assets.open(MODE_DATA_FILENAME).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val modeType = object : TypeToken<List<ModeFromJson>>() {}.type
                        val modesList: List<ModeFromJson> = Gson().fromJson(jsonReader, modeType)

                        modesList
                    }
                }
            } catch (ex: Exception) {
                Log.e(TAG, "Error reading modes", ex)
                null
            }
        }


    companion object {
        private val TAG = ModeNewRepository::class.java.simpleName
    }
}