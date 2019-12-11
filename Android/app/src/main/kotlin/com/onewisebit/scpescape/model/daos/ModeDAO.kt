package com.onewisebit.scpescape.model.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.onewisebit.scpescape.model.entities.Mode
import io.reactivex.Completable

@Dao
interface ModeDAO {
    /**
     * Get a mode by id.
     * @return the Mode from the table with a specific id.
     */
    @Query("SELECT * FROM modes WHERE mode_ID = :id")
    fun getModeById(id: Long): Mode

    /**
     * Get the minimum number of players from game mode id.
     * @return the minimum number of players needed to play this mode.
     */
    @Query("SELECT min_players FROM modes WHERE mode_ID = :id")
    fun getMinPlayers(id: Long): Int

    /**
     * Get the maximum number of players from game mode id.
     * @return the maximal number of players needed to play this mode.
     */
    @Query("SELECT max_players FROM modes WHERE mode_ID = :id")
    fun getMaxPlayers(id: Long): Int

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
     * @param modes the list of Modes to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(modes: List<Mode>)
}