package com.onewisebit.scpescape.game.model

import com.onewisebit.scpescape.game.IntroContract
import com.onewisebit.scpescape.model.entities.Game
import com.onewisebit.scpescape.model.entities.Mode
import com.onewisebit.scpescape.model.repositories.GameRepository
import io.reactivex.Single

class IntroModelImpl(gameRepository: GameRepository): IntroContract.IntroModel {

    private var gameRepo: GameRepository = gameRepository

    override fun getGame(id: Long): Single<Game> {
        return gameRepo.getGameById(id)
    }

    override fun getMode(id: Long): Single<Mode> {
        return gameRepo.getMode(id)
    }
}