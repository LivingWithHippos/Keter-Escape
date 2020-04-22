package com.onewisebit.scpescape.model.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.onewisebit.scpescape.model.entities.Round
import io.reactivex.Completable

@Dao
interface RoundDAO {

    /**
     * Insert a Round in the database. If the round already exists, replace it.
     * @param round the Round to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRound(round: Round): Long

    /**
     * Get all of the rounds from a game id.
     * @return the list of Rounds from the table with a specific game id.
     */
    @Query("SELECT * FROM rounds WHERE rounds.game = :gameID")
    suspend fun getRounds(gameID: Long): List<Round>

    /**
     * Get the number of saved round for a game
     * @return the list of Rounds from the table with a specific game id.
     */
    @Query("SELECT count(*) FROM rounds WHERE rounds.game = :gameID")
    suspend fun getRoundsNumber(gameID: Long): Int

    //todo: rename method or check if it can be moved to GameDao
    /**
     * Get the rounds mode
     * @return the mode id from the game table, which will be used for all its rounds.
     */
    @Query("SELECT games.mode FROM games WHERE games.game_ID = :gameID")
    suspend fun getRoundMode(gameID: Long): Int

    /**
     * Get a round's details code.
     * @return the details string from the table with a specific game id and round number.
     */
    @Query("SELECT rounds.details FROM rounds WHERE rounds.game = :gameID AND rounds.number = :roundNumber")
    suspend fun getRoundInfo(gameID: Long, roundNumber: Int): String

    /**
     * Get the current (or last) round of a Game.
     * @return the Round from the table with a specific game id.
     */
    @Query("SELECT * FROM rounds WHERE rounds.game = :gameID AND rounds.number = (Select MAX(rounds.number) FROM rounds WHERE rounds.game = :gameID)")
    suspend fun getCurrentRound(gameID: Long): Round

    /**
     * Get the current (or last) round's details code.
     * @return the details string from the table with a specific game id.
     */
    @Query("SELECT rounds.details FROM rounds WHERE rounds.game = :gameID AND rounds.number = (Select MAX(rounds.number) FROM rounds WHERE rounds.game = :gameID)")
    suspend fun getCurrentRoundDetails(gameID: Long): String

    /**
     * Delete all of a game's rounds
     */
    @Query("DELETE FROM rounds WHERE rounds.game = :gameID")
    fun deleteGameRounds(gameID: Long): Completable
}