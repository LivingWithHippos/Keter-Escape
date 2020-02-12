package com.onewisebit.scpescape.game.composable.model

import com.onewisebit.scpescape.game.composable.ContractGame
import com.onewisebit.scpescape.model.entities.Game
import com.onewisebit.scpescape.model.repositories.InGameRepository

class ModelGameImpl(
    val gameRepository: InGameRepository
) : ContractGame.ModelGame {

    override suspend fun getGame(gameID: Long): Game = gameRepository.getGameBlocking(gameID)
}