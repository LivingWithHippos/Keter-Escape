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
import com.onewisebit.scpescape.model.parsed.ModeDataClass
import com.onewisebit.scpescape.model.repositories.InModeJSONRepository
import com.onewisebit.scpescape.model.repositories.JSONRepository
import com.onewisebit.scpescape.utilities.MODE_FILE
import kotlinx.coroutines.coroutineScope

/**
 * Class used to pre-populate the database on first run
 */
class PopulateDatabaseModesWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val repo = JSONRepository(context)

    override suspend fun doWork(): Result = coroutineScope {

        val modePaths : MutableList<String> = repo.searchFile(MODE_FILE,maxDepth = 2)

        val database = SCPDatabase.getInstance(applicationContext)

        for (path in modePaths) {
            try {
                applicationContext.assets.open("asd.json").use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val modeType = object : TypeToken<List<ModeDataClass>>() {}.type
                        val modeDetails: List<ModeDataClass> = Gson().fromJson(jsonReader, modeType)
                        database.modeDAO().insertAll(modeDetails.map { Mode(it.id,it.name,it.description,it.rules,it.max,it.min) })
                    }
                }
            } catch (ex: Exception) {
                Log.e(TAG, "Error populating the mode database from path $path", ex)
                Result.failure()
            }
        }

        Result.success()
    }

    companion object {
        private val TAG = PopulateDatabaseModesWorker::class.java.simpleName
    }
}