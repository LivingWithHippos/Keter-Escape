package com.onewisebit.scpescape.model.daos

import androidx.room.*
import com.onewisebit.scpescape.model.entities.Game
import com.onewisebit.scpescape.model.entities.Mode
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface GameDAO {

    /**
     * Get a game by id.
     * @return the Game from the table with a specific id.
     */
    @Query("SELECT * FROM games WHERE game_ID = :id")
    fun getGameById(id: Long): Single<Game>

    /**
     * Get the game type from game id.
     * @return the game type.
     */
    @Query("SELECT game_type FROM games WHERE game_ID = :id")
    fun getType(id: Long): Int

    /**
     * Get the game mode id from game id.
     * @return the game mode id.
     */
    @Query("SELECT mode FROM games WHERE game_ID = :id")
    fun getModeID(id: Long): Int

    /**
     * Get the game mode id from game id.
     * @return the game mode id.
     */
    @Query("SELECT * FROM modes INNER JOIN games ON modes.mode_ID=games.mode WHERE game_ID = :gameID")
    fun getMode(gameID: Long): Single<Mode>

    /**
     * Insert a Game in the database. If the game already exists, replace it.
     * @param game the Game to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGame(game: Game): Long

    /**
     * Get the list of games.
     * @return the games from the table.
     */
    @Query("SELECT * FROM games")
    fun getAllGames(): List<Game>

    /**
     * Update a game in the database.
     * @param game the game to be updated.
     */
    @Update
    fun updateGame(game: Game): Completable

    /**
     * Remove a game from the database.
     */
    @Delete
    fun removeGame(game: Game)

    /**
     * Delete all games.
     */
    @Query("DELETE FROM games")
    fun deleteAllGames()

    /**
     * Mark a game as temporary (to be deleted if not started) or not
     */
    @Query("UPDATE games SET `temporary` = :temp  WHERE game_ID = :gameID")
    fun setTemporary(gameID: Long, temp: Boolean)

}