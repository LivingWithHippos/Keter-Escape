package com.onewisebit.scpescape.model.daos

import androidx.room.*
import com.onewisebit.scpescape.model.entities.Player
import io.reactivex.Completable

import io.reactivex.Flowable

@Dao
interface PlayerDAO {
    /**
     * Get a player by id.
     * @return the player from the table with a specific id.
     */
    @Query("SELECT * FROM players WHERE player_ID = :id")
    fun getPlayerById(id: String): Flowable<Player>

    /**
     * Get a list of players by searching their name.
     * @return the players from the table with a name containing the string.
     */
    @Query("SELECT * FROM players WHERE player_name LIKE '%' || :name || '%'")
    fun getPlayersByName(name: String): Flowable<List<Player>>

    /**
     * Insert a player in the database. If the player already exists, replace it.
     * @param player the player to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlayer(player: Player): Completable


    /**
     * Update a player in the database.
     * @param player the player to be updated.
     */
    @Update
    fun updatePlayer(player: Player): Completable

    /**
     * Delete all players.
     */
    @Query("DELETE FROM players")
    fun deleteAllPlayers()


    /**
     * Get the list of players.
     * @return the players from the table.
     */
    @Query("SELECT * FROM players")
    fun getAllPlayers(): Flowable<List<Player>>
}
