package com.onewisebit.scpescape.model.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.onewisebit.scpescape.model.entities.Participant
import com.onewisebit.scpescape.model.entities.Round
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface RoundDAO {

    /**
     * Insert a Round in the database. If the round already exists, replace it.
     * @param round the Round to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRound(round: Round): Completable

    /**
     * Get all of the rounds from a game id.
     * @return the list of Rounds from the table with a specific game id.
     */
    @Query("SELECT * FROM rounds WHERE rounds.game = :gameID")
    suspend fun getRounds(gameID: Long): List<Round>

    /**
     * Get the current (or last) round of a Game.
     * @return the Round from the table with a specific game id.
     */
    @Query("SELECT * FROM rounds WHERE rounds.game = :gameID AND rounds.number = (Select MAX(rounds.number) FROM rounds WHERE rounds.game = :gameID)")
    suspend fun getCurrentRound(gameID: Long): Round

    /**
     * Delete all of a game's rounds
     */
    @Query("DELETE FROM rounds WHERE rounds.game = :gameID")
    fun deleteGameRounds(gameID: Long): Completable
}