package com.onewisebit.scpescape.model.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.onewisebit.scpescape.model.entities.Round
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface RoundDAO {

    /**
     * Insert a Round in the database. If the round already exists, replace it.
     * @param round the Round to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRound(round: Round): Single<Int>

    /**
     * Get all of the rounds from a game id.
     * @return the list of Rounds from the table with a specific game id.
     */
    @Query("SELECT * FROM rounds WHERE rounds.game = :gameID")
    fun getRounds(gameID: Long): Flowable<List<Round>>

    /**
     * Delete all of a game's rounds
     */
    @Query("DELETE FROM rounds WHERE rounds.game = :gameID")
    fun deleteGameRounds(gameID: Long): Completable
}