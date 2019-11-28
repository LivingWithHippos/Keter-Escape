package com.onewisebit.scp_scarycontainmentpanic.model

import com.onewisebit.scp_scarycontainmentpanic.GameSettingsContract

class GameSettingsModelImpl(gameRepository: GameRepository) :
    GameSettingsContract.GameSettingsModel {

    private var repository: GameRepository = gameRepository

    //TODO: check if returned game is needed
    override fun createGame(gameType: Int): Game {
        val game = Game(0, gameType, true)
        repository.insertGame(game)
        return game
    }

    override fun saveGame(game: Game) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}