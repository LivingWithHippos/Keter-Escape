package com.onewisebit.scpescape.model.daos

import androidx.room.*
import com.onewisebit.scpescape.model.entities.Participant
import com.onewisebit.scpescape.model.entities.Player
import com.onewisebit.scpescape.model.entities.Role
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
    fun getGameParticipants(gameID: Long): Flowable<List<Participant>>

    //TODO: remove getGameParticipants or getGameParticipantsSingle when it's decided which one is better
    /**
     * Get a participant by id.
     * @return the Participant from the table with a specific game and player id.
     */
    @Query("SELECT * FROM participants WHERE game = :gameID")
    fun getGameParticipantsSingle(gameID: Long): Single<List<Participant>>

    /**
     * Get a participant by id.
     * @return the Participant from the table with a specific game and player id.
     */
    @Query("SELECT * FROM participants WHERE game = :gameID")
    suspend fun getGameParticipantsBlocking(gameID: Long): List<Participant>

    /**
     * Get a participant by id.
     * @return the Players corresponding to the participants.
     */
    @Query("SELECT players.* FROM players INNER JOIN participants ON players.player_ID = participants.player WHERE participants.game = :gameID")
    fun getGamePlayers(gameID: Long): Single<List<Player>>

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
     * Get the role of a participant from a game and player id.
     * @return the Role from the table with a specific game and player id.
     */
    @Query("SELECT roles.* FROM roles INNER JOIN participants ON roles.role_name = participants.role WHERE participants.game = :gameID AND participants.player = :playerID")
    fun getParticipantRole(gameID: Long, playerID: Long): Single<Role>

    /**
     * Set the role of a participant from a game, role and player id.
     * @return the Role from the table with a specific game and player id.
     */
    @Query("UPDATE participants SET role = :roleName WHERE participants.game = :gameID AND participants.player = :playerID")
    suspend fun setParticipantRole(gameID: Long, playerID: Long, roleName: String)

    /**
     * Get the role of a participant from a game and player id.
     * @return the Role from the table with a specific game and player id.
     */
    @Query("SELECT roles.* FROM roles INNER JOIN participants ON roles.role_name = participants.role WHERE participants.game = :gameID")
    fun getParticipantsRoles(gameID: Long): Single<List<Role>>

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
     * Remove a participant by game and player id.
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