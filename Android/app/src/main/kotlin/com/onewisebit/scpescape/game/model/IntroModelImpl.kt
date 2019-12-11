package com.onewisebit.scpescape.game.model

import com.onewisebit.scpescape.game.IntroContract
import com.onewisebit.scpescape.model.entities.Game
import com.onewisebit.scpescape.model.repositories.GameRepository

class IntroModelImpl(gameRepository: GameRepository): IntroContract.IntroModel {

    private var gameRepo: GameRepository = gameRepository

    override fun getGame(id: Long): Game {
        return gameRepo.getGameById(id)
    }
}