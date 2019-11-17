package com.onewisebit.scp_scarycontainmentpanic.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable

@Dao
interface ModeDAO {
    /**
     * Get a mode by id.
     * @return the Mode from the table with a specific id.
     */
    @Query("SELECT * FROM modes WHERE modeID = :id")
    fun getModeById(id: Int): Mode

    /**
     * Insert a Mode in the database. If the mode already exists, replace it.
     * @param mode the Mode to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMode(mode: Mode): Completable

    /**
     * Get the list of modes.
     * @return the modes from the table.
     */
    @Query("SELECT * FROM modes")
    fun getAllModes(): List<Mode>

    /**
     * Insert a list of Modes in the database. If the mode already exists, replace it.
     * @param mode the Mode to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(modes: List<Mode>)
}