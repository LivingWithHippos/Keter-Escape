package com.onewisebit.scpescape.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.onewisebit.scpescape.model.daos.*
import com.onewisebit.scpescape.model.entities.*
import com.onewisebit.scpescape.utilities.DATABASE_NAME
import com.onewisebit.scpescape.workers.PopulateDatabaseModesWorker
import com.onewisebit.scpescape.workers.PopulateDatabaseRolesWorker

/**
 * The Room database that contains the Users table
 */
@Database(
    entities = [Player::class, Role::class, Game::class, Mode::class, Participant::class],
    version = 1
)
abstract class SCPDatabase : RoomDatabase() {

    abstract fun playerDAO(): PlayerDAO
    abstract fun roleDAO(): RoleDAO
    abstract fun gameDAO(): GameDAO
    abstract fun modeDAO(): ModeDAO
    abstract fun participantDAO(): ParticipantDAO

    companion object {

        @Volatile
        private var INSTANCE: SCPDatabase? = null

        fun getInstance(context: Context): SCPDatabase =
            INSTANCE
                ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(
                        context
                    ).also { INSTANCE = it }
            }

        //TODO: add option for updates to the scps json file, check RoomDatabase.Builder.createFromAsset()
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                SCPDatabase::class.java, DATABASE_NAME
            )
                .addCallback(object : RoomDatabase.Callback() {
                    //populate the database on creation
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val roleRequest = OneTimeWorkRequestBuilder<PopulateDatabaseRolesWorker>().build()
                        WorkManager.getInstance(context).enqueue(roleRequest)
                        val modeRequest = OneTimeWorkRequestBuilder<PopulateDatabaseModesWorker>().build()
                        WorkManager.getInstance(context).enqueue(modeRequest)
                    }
                })
                .build()
    }
}