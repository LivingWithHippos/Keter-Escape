package com.onewisebit.scp_scarycontainmentpanic.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable

import io.reactivex.Flowable

interface PlayerDAO {
    /**
     * Get a user by id.
     * @return the player from the table with a specific id.
     */
    @Query("SELECT * FROM players WHERE playerid = :id")
    fun getPlayerById(id: String): Flowable<Player>

    /**
     * Insert a player in the database. If the player already exists, replace it.
     * @param player the player to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlayer(player: Player): Completable

    /**
     * Delete all players.
     */
    @Query("DELETE FROM players")
    fun deleteAllPlayers()
}
