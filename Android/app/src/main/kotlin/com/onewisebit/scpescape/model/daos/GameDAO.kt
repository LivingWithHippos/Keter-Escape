package com.onewisebit.scpescape.model.daos

import androidx.room.*
import com.onewisebit.scpescape.model.entities.Game
import io.reactivex.Completable

@Dao
interface GameDAO {

    /**
     * Get a game by id.
     * @return the Game from the table with a specific id.
     */
    @Query("SELECT * FROM games WHERE game_ID = :id")
    fun getGameById(id: Long): Game

    /**
     * Get the game type from game id.
     * @return the game type.
     */
    @Query("SELECT game_type FROM games WHERE game_ID = :id")
    fun getType(id: Long): Int

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

}