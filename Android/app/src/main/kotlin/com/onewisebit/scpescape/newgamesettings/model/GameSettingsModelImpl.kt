package com.onewisebit.scpescape.newgamesettings.model

import com.onewisebit.scpescape.model.entities.Game
import com.onewisebit.scpescape.model.entities.Mode
import com.onewisebit.scpescape.model.repositories.GameRepository
import com.onewisebit.scpescape.model.repositories.ModeRepository
import com.onewisebit.scpescape.newgamesettings.GameSettingsContract
import io.reactivex.Single

class GameSettingsModelImpl(gameRepository: GameRepository, modeRepository: ModeRepository) :
    GameSettingsContract.GameSettingsModel {

    private var gameRepo: GameRepository = gameRepository
    private var modeRepo: ModeRepository = modeRepository

    //TODO: check if returned game is needed
    override fun createGame(gameMode: Int, gameType: Int): Single<Long> {
        val game = Game(0, gameMode, gameType, true)
        return gameRepo.insertGame(game)
    }

    override fun saveGame(game: Game) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMode(gameMode: Int): Single<Mode> {
        return modeRepo.getMode(gameMode)
    }

}