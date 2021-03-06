package com.onewisebit.scpescape.model.repositories

import com.onewisebit.scpescape.model.entities.Player
import io.reactivex.Flowable

interface InPlayerRepository {

    fun insertPlayer(player: Player)

    fun updatePlayer(player: Player)

    suspend fun getPlayerById(id: Long): Player?

    fun getAllPlayers(): Flowable<List<Player>>

    fun deleteAllPlayers()

    fun getPlayersByName(name: String): Flowable<List<Player>>

    suspend fun getPlayersByGame(gameID: Long): List<Player>

    suspend fun getPlayerName(playerId: Long): String?
}