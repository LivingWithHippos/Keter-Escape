package com.onewisebit.scpescape.game.composable.model

import com.onewisebit.scpescape.game.composable.ContractPlayer
import com.onewisebit.scpescape.model.entities.Player
import com.onewisebit.scpescape.model.repositories.InPlayerRepository

class ModelPlayerImpl(
    val playerRepository: InPlayerRepository
) : ContractPlayer.ModelPlayer {
    override suspend fun getPlayers(gameID: Long): List<Player> =
        playerRepository.getPlayersByGame(gameID)

    override suspend fun getPlayer(id: Long): Player? = playerRepository.getPlayerById(id)

    override suspend fun getPlayerName(playerId: Long): String? =
        playerRepository.getPlayerName(playerId)
}