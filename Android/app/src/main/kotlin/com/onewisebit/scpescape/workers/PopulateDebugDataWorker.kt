package com.onewisebit.scpescape.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.onewisebit.scpescape.model.database.SCPDatabase
import com.onewisebit.scpescape.model.entities.Player
import kotlinx.coroutines.coroutineScope

class PopulateDebugDataWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {

        val database = SCPDatabase.getInstance(applicationContext)
        val playerDao = database.playerDAO()
        try {
            playerDao.insertPlayerCoroutine(Player(
                0,
                "Adalberto",
                ""
            ))
            playerDao.insertPlayerCoroutine(Player(
                1,
                "Astolfo",
                ""
            ))
            playerDao.insertPlayerCoroutine(Player(
                2,
                "Basileo",
                ""
            ))
            playerDao.insertPlayerCoroutine(Player(
                3,
                "Dagoberto",
                ""
            ))
            playerDao.insertPlayerCoroutine(Player(
                4,
                "Ermenegildo",
                ""
            ))
            playerDao.insertPlayerCoroutine(Player(
                5,
                "Fulgenzio",
                ""
            ))
            playerDao.insertPlayerCoroutine(Player(
                6,
                "Giangilberto",
                ""
            ))
            playerDao.insertPlayerCoroutine(Player(
                7,
                "Liutprando",
                ""
            ))

        } catch (ex: Exception) {
            Log.e(TAG, "Error adding data for debugging purpose to the database ", ex)
            return@coroutineScope Result.failure()
        }
        Result.success()
    }

    companion object {
        private val TAG = PopulateDebugDataWorker::class.java.simpleName
    }
}