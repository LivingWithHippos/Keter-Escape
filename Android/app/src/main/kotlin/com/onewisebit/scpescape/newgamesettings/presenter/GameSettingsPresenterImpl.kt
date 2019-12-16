package com.onewisebit.scpescape.newgamesettings.presenter

import com.onewisebit.scpescape.model.entities.Game
import com.onewisebit.scpescape.model.entities.Mode
import com.onewisebit.scpescape.newgamesettings.GameSettingsContract
import io.reactivex.Single

class GameSettingsPresenterImpl(
    gView: GameSettingsContract.GameSettingsView,
    gModel: GameSettingsContract.GameSettingsModel
) : GameSettingsContract.GameSettingsPresenter {

    private var view: GameSettingsContract.GameSettingsView = gView
    private var model: GameSettingsContract.GameSettingsModel = gModel

    override fun getNewGame(gameMode: Int, gameType: Int): Single<Long> {
        return model.createGame(gameMode, gameType)
    }

    override fun saveSetting(key: String, value: String) {
        TODO("Save the settings chosen")
    }

    override fun gameSettingsAccepted(game: Game) {
        model.saveGame(game)
    }

    override fun getMode(gameMode: Int): Single<Mode> {
        return model.getMode(gameMode)
    }



}