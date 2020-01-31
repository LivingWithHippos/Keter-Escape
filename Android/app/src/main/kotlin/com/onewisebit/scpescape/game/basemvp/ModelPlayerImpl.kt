package com.onewisebit.scpescape.game.basemvp

import com.onewisebit.scpescape.model.entities.Player
import com.onewisebit.scpescape.model.repositories.InPlayerRepository

class ModelPlayerImpl(
    val playerRepository: InPlayerRepository
) : ContractPlayer.ModelPlayer {
    override suspend fun getPlayers(gameID: Long): List<Player> =
        playerRepository.getPlayersByGame(gameID)
}