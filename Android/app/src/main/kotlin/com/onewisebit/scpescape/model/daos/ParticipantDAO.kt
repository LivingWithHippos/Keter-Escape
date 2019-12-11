package com.onewisebit.scpescape.model.daos

import androidx.room.*
import com.onewisebit.scpescape.model.entities.Participant
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

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
     * Get a participant by id.
     * @return the Participant from the table with a specific game and player id.
     */
    @Query("SELECT player FROM participants WHERE game = :gameID")
    fun getGameParticipantsID(gameID: Long): Flowable<List<Long>>

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
    fun removeParticipant(participant: Participant): Completable

    /**
     * Remove a participant by game and player id.ì
     */
    @Query("DELETE FROM participants WHERE game = :gameID AND player = :playerID")
    fun removeParticipant(gameID: Long, playerID: Long): Completable

    /**
     * Delete all participants.
     */
    @Query("DELETE FROM participants")
    fun deleteAllParticipants()

    /**
     * Return the number of participants in a game
     */
    @Query("SELECT COUNT(*) FROM participants WHERE game = :gameID")
    fun getParticipantNumber(gameID: Long): Single<Int>
}