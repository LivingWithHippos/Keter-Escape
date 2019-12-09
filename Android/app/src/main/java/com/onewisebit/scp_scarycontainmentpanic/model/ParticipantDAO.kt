package com.onewisebit.scp_scarycontainmentpanic.model

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface ParticipantDAO {

    /**
     * Get a participant by id.
     * @return the Participant from the table with a specific game and player id.
     */
    @Query("SELECT * FROM participants WHERE game = :gameID AND player = :playerID")
    fun getParticipantById(gameID: Long, playerID: Long): Participant

    /**
     * Get a participant by id.
     * @return the Participant from the table with a specific game and player id.
     */
    @Query("SELECT * FROM participants WHERE game = :gameID")
    fun getGameParticipantList(gameID: Long): Flowable<List<Participant>>

    /**
     * Get the state of a participant by id.
     * @return the state from the table with a specific game and player id.
     */
    @Query("SELECT state FROM participants WHERE game = :gameID AND player = :playerID")
    fun getParticipantState(gameID: Long, playerID: Long): Int

    /**
     * Insert a participant in the database. If the participant already exists, replace it.
     * @param participant the player to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertParticipant(participant: Participant): Completable

    /**
     * Update a participant in the database.
     * @param participant the player to be updated.
     */
    @Update
    fun updateParticipant(participant: Participant): Completable

    /**
     * Remove a participant from the database.
     */
    @Delete
    fun removeParticipant(participant: Participant)

    /**
     * Delete all participants.
     */
    @Query("DELETE FROM participants")
    fun deleteAllParticipants()
}