package com.onewisebit.scpescape.game.model

import com.onewisebit.scpescape.game.IntroContract
import com.onewisebit.scpescape.model.ModeDataClass
import com.onewisebit.scpescape.model.entities.Game
import com.onewisebit.scpescape.model.repositories.GameRepository
import com.onewisebit.scpescape.model.repositories.InModeRepository
import com.onewisebit.scpescape.model.repositories.InModelNewRepository
import com.onewisebit.scpescape.model.repositories.ModeNewRepository
import io.reactivex.Single

class IntroModelImpl(gameRepository: GameRepository, modeRepository: InModelNewRepository) : IntroContract.IntroModel {

    private var gameRepo: GameRepository = gameRepository
    private var modeRepo: InModelNewRepository = modeRepository

    override suspend fun getGame(id: Long): Game {
        return gameRepo.getGameBlocking(id)
    }

    override suspend fun getMode(id: Long): ModeDataClass? {
        return modeRepo.getMode(gameRepo.getModeId(id))
    }
}