package com.onewisebit.scpescape.model.daos

import androidx.room.*
import com.onewisebit.scpescape.model.entities.Save

@Dao
interface SaveDAO {

    /**
     * Get a save by game id.
     * @return the Save from the table with a specific game id.
     */
    @Query("SELECT * FROM saves WHERE game = :gameID")
    suspend fun getSaveByGameId(gameID: Long): Save

    /**
     * Insert a Save in the database. If the save already exists, replace it.
     * @param save the Save to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSave(save: Save)

    /**
     * Get the list of games.
     * @return the games from the table.
     */
    @Query("SELECT * FROM saves")
    suspend fun getAllSaves(): List<Save>

    /**
     * Remove a save from the database.
     */
    @Delete
    suspend fun removeSave(save: Save)

    /**
     * Delete all saves.
     */
    @Query("DELETE FROM saves")
    fun deleteAllSaves()

    /**
     * Saves the save's finite state machine states.
     */
    @Query("UPDATE saves SET state_machine_old = :oldState, state_machine_new = :newState  WHERE game = :gameID")
    suspend fun setMachineStates(gameID: Long, oldState: String, newState: String)

    /**
     * Saves the save's current player.
     */
    @Query("UPDATE saves SET player = :playerID WHERE game = :gameID")
    suspend fun setCurrentPlayer(gameID: Long, playerID: Long)

    /**
     * Saves the save's state's state.
     */
    @Query("UPDATE saves SET state_processed = :processed WHERE game = :gameID")
    suspend fun setStateProcessed(gameID: Long, processed: Boolean)
}