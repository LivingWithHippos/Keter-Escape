package com.onewisebit.scpescape.newgamesettings.model

import com.onewisebit.scpescape.model.entities.Game
import com.onewisebit.scpescape.model.parsed.ModeDataClass
import com.onewisebit.scpescape.model.repositories.InGameRepository
import com.onewisebit.scpescape.model.repositories.InModeJSONRepository
import com.onewisebit.scpescape.newgamesettings.GameSettingsContract
import io.reactivex.Single

class GameSettingsModelImpl(
    gameRepository: InGameRepository,
    modeRepository: InModeJSONRepository
) :
    GameSettingsContract.GameSettingsModel {

    private var gameRepo: InGameRepository = gameRepository
    private var modeRepo: InModeJSONRepository = modeRepository

    //TODO: check if returned game is needed
    override fun createGame(gameMode: Int, gameType: Int): Single<Long> {
        val game = Game(0, gameMode, gameType, true)
        return gameRepo.insertGame(game)
    }

    override fun saveGame(game: Game) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getMode(gameMode: Int): ModeDataClass? {
        return modeRepo.getMode(gameMode)
    }

}