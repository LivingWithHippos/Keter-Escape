package com.onewisebit.scpescape.model.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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
    suspend fun insertTurn(turn: Turn)

    /**
     * Get all of the turns from a game.
     * @return the list of Turns from the table with a specific game id.
     */
    @Query("SELECT * FROM turns WHERE turns.game = :gameID")
    suspend fun getGameTurns(gameID: Long): List<Turn>

    /**
     * Get all of the turns from a game's player.
     * @return the list of Turns from the table with a specific game and player id.
     */
    @Query("SELECT * FROM turns WHERE turns.game = :gameID AND turns.player = :playerID")
    fun getPlayerTurns(gameID: Long, playerID: Long): Flowable<List<Turn>>

    /**
     * Get all of the turns from a round.
     * @return the list of Turns from the table with a specific game id and round number or null if there aren't any
     */
    @Query("SELECT * FROM turns WHERE turns.game = :gameID AND turns.round = :roundNumber")
    suspend fun getRoundTurns(gameID: Long, roundNumber: Int): List<Turn>?

    /**
     * Get all the turns from the last round.
     * @return the list of Turns from the table with a specific game id and its greatest round number. If the last round has no turns return null.
     */
    @Query("SELECT turns.* FROM turns WHERE turns.game = :gameID AND turns.round = (SELECT MAX(number) FROM rounds WHERE rounds.game = :gameID)")
    suspend fun getLatestRoundTurns(gameID: Long): List<Turn>?

    /* This query works like this:
    * get all the alive participants of a game
    * get the turns from the last created round of the game.
    * remove the participants who already have played turns in this round
    */
    /**
     * Get all the players id of the participants who haven't played the last round of a game.
     */
    @Query("SELECT participants.player FROM participants WHERE participants.game = :gameID AND participants.state = 1 AND participants.player NOT IN (SELECT turns.player FROM turns WHERE turns.game = :gameID AND turns.round = (SELECT MAX(number) FROM rounds WHERE rounds.game = :gameID))")
    suspend fun getLatestRoundMissingPlayers(gameID: Long): List<Long>?

    /**
     * Get the last created turn for a game. Should be the only "active" one.
     * @return the latest Turn of the latest round from the table with a specific game id or null if there are none.
     */
    //turns starts from 0 and go on, they don't restart from 0 on a new Round, only in a new Game
    @Query("SELECT * FROM turns WHERE game = :gameID AND turn_number = (SELECT MAX(turn_number) FROM turns WHERE game = :gameID)")
    suspend fun getLastTurn(gameID: Long): Turn?

    /**
     * Delete all of a game's turns
     */
    @Query("DELETE FROM turns WHERE turns.game = :gameID")
    fun deleteGameTurns(gameID: Long): Completable

}

