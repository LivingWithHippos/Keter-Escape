package com.onewisebit.scpescape.model.repositories

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.onewisebit.scpescape.model.ModeDataClass
import com.onewisebit.scpescape.utilities.MODE_DATA_FILENAME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ModeNewRepository(private val context: Context): InModelNewRepository {

    override suspend fun getMode(id: Int): ModeDataClass? =
    withContext(Dispatchers.IO) {
        try {
            // retrieving modes
            context.assets.open(MODE_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val modeType = object : TypeToken<List<ModeDataClass>>() {}.type
                    val modesList: List<ModeDataClass> = Gson().fromJson(jsonReader, modeType)

                    val modeFound = modesList.filter{
                        mode -> mode.id == id
                    }

                    if (modeFound.isNotEmpty())
                        modeFound[0]
                    else
                        null
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error reading modes", ex)
            // is there a better way to handle this?
            // if we can't read the file null seems a good return value
            null
        }
    }

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


    companion object {
        private val TAG = ModeNewRepository::class.java.simpleName
    }
}