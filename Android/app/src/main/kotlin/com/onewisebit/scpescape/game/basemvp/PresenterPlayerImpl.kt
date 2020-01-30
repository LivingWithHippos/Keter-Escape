package com.onewisebit.scpescape.game.basemvp

import com.onewisebit.scpescape.model.entities.Player

class PresenterPlayerImpl (val model: ContractPlayer.ModelPlayer, val gameId: Long): ContractPlayer.PresenterPlayer {
    override suspend fun getPlayers(): List<Player> = model.getPlayers(gameId)

}