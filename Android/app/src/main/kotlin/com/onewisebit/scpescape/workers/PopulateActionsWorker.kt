package com.onewisebit.scpescape.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.onewisebit.scpescape.model.ActionVote
import com.onewisebit.scpescape.utilities.ACTION_DATA_FILENAME
import kotlinx.coroutines.coroutineScope

class PopulateActionsWorker (
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            // Populating roles
            applicationContext.assets.open(ACTION_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val roleType = object : TypeToken<List<ActionVote>>() {}.type
                    val rolesList: List<ActionVote> = Gson().fromJson(jsonReader, roleType)

                    Log.d(TAG,"Lemme debug")
                }
                Result.success()
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error reading actions", ex)
            Result.failure()
        }
    }

    companion object {
        private val TAG = PopulateDatabaseRolesWorker::class.java.simpleName
    }
}