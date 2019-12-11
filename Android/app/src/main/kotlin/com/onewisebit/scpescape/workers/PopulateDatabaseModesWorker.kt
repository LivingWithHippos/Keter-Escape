package com.onewisebit.scpescape.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.onewisebit.scpescape.model.database.SCPDatabase
import com.onewisebit.scpescape.model.entities.Mode
import com.onewisebit.scpescape.utilities.MODE_DATA_FILENAME
import kotlinx.coroutines.coroutineScope

/**
 * Class used to pre-populate the database on first run
 */
class PopulateDatabaseModesWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            // Populating roles
            applicationContext.assets.open(MODE_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val modeType = object : TypeToken<List<Mode>>() {}.type
                    val modesList: List<Mode> = Gson().fromJson(jsonReader, modeType)

                    val database = SCPDatabase.getInstance(applicationContext)
                    database.modeDAO().insertAll(modesList)
                }
                Result.success()
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error populating the database", ex)
            Result.failure()
        }
    }

    companion object {
        private val TAG = PopulateDatabaseRolesWorker::class.java.simpleName
    }
}