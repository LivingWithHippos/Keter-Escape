package com.onewisebit.scp_scarycontainmentpanic.model

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

/**
 * The Room database that contains the Users table
 */
@Database(entities = [Player::class, Role::class], version = 1)
abstract class SCPDatabase : RoomDatabase() {

    abstract fun playerDAO(): PlayerDAO
    abstract fun roleDAO(): RoleDAO

    companion object {

        @Volatile private var INSTANCE: SCPDatabase? = null

        fun getInstance(context: Context): SCPDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                SCPDatabase::class.java, "SCP.db")
                .build()
    }
}