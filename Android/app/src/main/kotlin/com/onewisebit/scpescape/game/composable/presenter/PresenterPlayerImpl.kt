package com.onewisebit.scpescape.game.composable.presenter

import com.onewisebit.scpescape.game.composable.ContractPlayer
import com.onewisebit.scpescape.model.entities.Participant
import com.onewisebit.scpescape.model.entities.Player

class PresenterPlayerImpl(val model: ContractPlayer.ModelPlayer, val gameId: Long) :
    ContractPlayer.PresenterPlayer {
    override suspend fun getPlayers(): List<Player> = model.getPlayers(gameId)

    override suspend fun getPlayers(participantList: List<Participant>): List<Player> {
        val players: MutableList<Player> = mutableListOf()
        for (p in participantList)
            players.add(getPlayer(p.playerID))
        return players
    }

    override suspend fun getPlayer(id: Long): Player {
        return model.getPlayer(id) ?: throw IllegalArgumentException("No Player found for id $id")
    }

}