package com.onewisebit.scp_scarycontainmentpanic.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.onewisebit.scp_scarycontainmentpanic.utilities.DATABASE_NAME
import com.onewisebit.scp_scarycontainmentpanic.workers.PopulateDatabaseWorker

/**
 * The Room database that contains the Users table
 */
@Database(entities = [Player::class, Role::class], version = 1)
abstract class SCPDatabase : RoomDatabase() {

    abstract fun playerDAO(): PlayerDAO
    abstract fun roleDAO(): RoleDAO

    companion object {

        @Volatile
        private var INSTANCE: SCPDatabase? = null

        fun getInstance(context: Context): SCPDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
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
                        val request = OneTimeWorkRequestBuilder<PopulateDatabaseWorker>().build()
                        WorkManager.getInstance(context).enqueue(request)
                    }
                })
                .build()
    }
}