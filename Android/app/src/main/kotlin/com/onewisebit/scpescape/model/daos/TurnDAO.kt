package com.onewisebit.scpescape.model.daos

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.onewisebit.scpescape.model.entities.Turn
import io.reactivex.Flowable

interface TurnDAO {

    /**
     * Insert a Turn in the database. If the turn already exists, replace it.
     * @param turn the Turn to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTurn(turn: Turn)

    /**
     * Insert a Turn in the database. If the turn already exists, replace it.
     * @param game the id of the game where the turn is being played
     * @param round the number of the round where the turn is being played
     * @param player the id of the player playing the turn
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTurn(round: Int,game: Long, player: Long)

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
     * Delete all of a game's turns
     */
    @Query("DELETE FROM turns WHERE turns.game = :gameID")
    fun deleteGameTurns(gameID: Long)
}
