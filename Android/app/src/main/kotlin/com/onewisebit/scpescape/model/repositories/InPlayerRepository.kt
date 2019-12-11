package com.onewisebit.scpescape.model.repositories

import com.onewisebit.scpescape.model.entities.Player
import io.reactivex.Flowable

interface InPlayerRepository {

    fun insertPlayer(player: Player)

    fun updatePlayer(player: Player)

    fun getPlayerById(id: String): Flowable<Player>

    fun getAllPlayers(): Flowable<List<Player>>

    fun deleteAllPlayers()

    fun getPlayersByName(name: String): Flowable<List<Player>>
}