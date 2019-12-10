package com.onewisebit.scp_scarycontainmentpanic.model

import com.onewisebit.scp_scarycontainmentpanic.GameSettingsContract
import io.reactivex.Single

class GameSettingsModelImpl(gameRepository: GameRepository) :
    GameSettingsContract.GameSettingsModel {

    private var repository: GameRepository = gameRepository

    //TODO: check if returned game is needed
    override fun createGame(gameType: Int): Single<Long> {
        val game = Game(0, gameType, true)
        return repository.insertGame(game)
    }

    override fun saveGame(game: Game) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}