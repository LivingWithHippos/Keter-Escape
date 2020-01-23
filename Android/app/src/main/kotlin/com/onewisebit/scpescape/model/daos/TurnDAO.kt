package com.onewisebit.scpescape.model.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.onewisebit.scpescape.model.entities.Participant
import com.onewisebit.scpescape.model.entities.Player
import com.onewisebit.scpescape.model.entities.Turn
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface TurnDAO {

    /**
     * Insert a Turn in the database. If the turn already exists, replace it.
     * @param turn the Turn to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTurn(turn: Turn): Completable

    /**
     * Get all of the turns from a game.
     * @return the list of Turns from the table with a specific game id.
     */
    @Query("SELECT * FROM turns WHERE turns.game = :gameID")
    fun getGameTurns(gameID: Long): Flowable<List<Turn>>

    /**
     * Get all of the turns from a game's player.
     * @return the list of Turns from the table with a specific game and player id.
     */
    @Query("SELECT * FROM turns WHERE turns.game = :gameID AND turns.player = :playerID")
    fun getPlayerTurns(gameID: Long, playerID: Long): Flowable<List<Turn>>

    /**
     * Get all of the turns from a round.
     * @return the list of Turns from the table with a specific game id and round number.
     */
    @Query("SELECT * FROM turns WHERE turns.game = :gameID AND turns.round = :roundNumber")
    fun getRoundTurns(gameID: Long, roundNumber: Int): Flowable<List<Turn>>

    /**
     * Get all the played turns from the last round (completed or not)
     * @return the list of Turns from the table with a specific game id and its greatest round number.
     */
    @Query("SELECT * FROM turns WHERE turns.game = :gameID AND turns.round = (SELECT MAX(round) FROM turns WHERE turns.game = :gameID)")
    fun getLastRoundTurns(gameID: Long): Flowable<List<Turn>>

    /**
     * Get the last created turn for a game. Should be the only "active" one.
     * @return the latest Turn of the latest round from the table with a specific game id.
     */
    //TODO: what if we count turns from 0 or 1 and just go on? No reset on new round.
    @Query("SELECT * FROM turns WHERE game = :gameID AND turn_number = (SELECT MAX(turn_number) FROM turns WHERE game = :gameID)")
    fun getLastTurn(gameID: Long): Single<Turn>

    /**
     * Delete all of a game's turns
     */
    @Query("DELETE FROM turns WHERE turns.game = :gameID")
    fun deleteGameTurns(gameID: Long): Completable

    @Query("SELECT participants.* FROM participants INNER JOIN turns ON turns.player = participants.player WHERE turns.game = :gameID AND turns.turn_number = (SELECT max(turn_number) FROM turns WHERE turns.game = :gameID )")
    suspend fun getCurrentParticipant(gameID: Long): Participant

}

