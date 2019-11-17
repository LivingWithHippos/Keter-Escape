package com.onewisebit.scp_scarycontainmentpanic.model

import io.reactivex.Flowable

interface InPlayerRepository {

    fun insertPlayer(player: Player)

    fun updatePlayer(player: Player)

    fun getPlayerById(id: String): Flowable<Player>

    fun getAllPlayers(): Flowable<List<Player>>

    fun deleteAllPlayers()
}