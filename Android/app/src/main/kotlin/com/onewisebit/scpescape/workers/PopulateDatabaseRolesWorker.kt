package com.onewisebit.scpescape.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.onewisebit.scpescape.model.database.SCPDatabase
import com.onewisebit.scpescape.model.entities.Role
import com.onewisebit.scpescape.model.parsed.ModeDataClass
import com.onewisebit.scpescape.model.parsed.RoleDetails
import com.onewisebit.scpescape.model.repositories.JSONRepository
import com.onewisebit.scpescape.utilities.MODE_FILE
import com.onewisebit.scpescape.utilities.ROLE_FILE
import com.onewisebit.scpescape.utilities.ROLE_FOLDER
import kotlinx.coroutines.coroutineScope

/**
 * Class used to pre-populate the database on first run
 */
class PopulateDatabaseRolesWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val repo = JSONRepository(context)

    override suspend fun doWork(): Result = coroutineScope {
        // leggi modalità
        // leggi ruoli
        // mappa e popola db

        val modePaths: MutableList<String> = repo.searchFile(MODE_FILE, maxDepth = 2)
        val database = SCPDatabase.getInstance(applicationContext)

        for (path in modePaths) {
            val modeDetails: MutableList<ModeDataClass> = mutableListOf()
            try {
                applicationContext.assets.open(path).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val modeType = object : TypeToken<List<ModeDataClass>>() {}.type
                        val added = modeDetails.addAll(Gson().fromJson(jsonReader, modeType))
                    }
                }
            } catch (ex: Exception) {
                Log.e(TAG, "Error getting the mode from path $path", ex)
                Result.failure()
            }

            val rolePath = path.removeSuffix(MODE_FILE).plus(ROLE_FOLDER).plus(ROLE_FILE)

            try {
                applicationContext.assets.open(rolePath).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val roleType = object : TypeToken<List<RoleDetails>>() {}.type
                        val rolesList: List<RoleDetails> = Gson().fromJson(jsonReader, roleType)
                        val modeId = modeDetails[0].id
                        database.roleDAO().insertAll(rolesList.map {
                            Role(
                                modeId,
                                it.name,
                                it.description,
                                it.group
                            )
                        })
                    }
                    Result.success()
                }
            } catch (ex: Exception) {
                Log.e(TAG, "Error populating the role database for path $path", ex)
                Result.failure()
            }
        }

        Result.success()
    }

    companion object {
        private val TAG = PopulateDatabaseRolesWorker::class.java.simpleName
    }
}