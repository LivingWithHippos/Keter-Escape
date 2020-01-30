package com.onewisebit.scpescape.model.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.onewisebit.scpescape.model.entities.Mode
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ModeDAO {
    /**
     * Get a mode by id.
     * @return the Mode from the table with a specific id.
     */
    @Query("SELECT * FROM modes WHERE mode_ID = :id")
    fun getModeById(id: Int): Single<Mode>

    /**
     * Get a mode id from a game.
     * @return the mode id from the table with a specific game id.
     */
    @Query("SELECT games.mode FROM games WHERE games.game_ID = :gameId")
    suspend fun getGameModeId(gameId: Long): Int

    /**
     * Get the minimum number of players from game mode id.
     * @return the minimum number of players needed to play this mode.
     */
    @Query("SELECT min_players FROM modes WHERE mode_ID = :id")
    fun getMinPlayers(id: Int): Int

    /**
     * Get the maximum number of players from game mode id.
     * @return the maximal number of players needed to play this mode.
     */
    @Query("SELECT max_players FROM modes WHERE mode_ID = :id")
    fun getMaxPlayers(id: Int): Int

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