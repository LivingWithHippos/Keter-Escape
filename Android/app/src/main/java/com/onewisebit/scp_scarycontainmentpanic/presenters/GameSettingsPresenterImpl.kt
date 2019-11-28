package com.onewisebit.scp_scarycontainmentpanic.presenters

import com.onewisebit.scp_scarycontainmentpanic.GameSettingsContract
import com.onewisebit.scp_scarycontainmentpanic.model.Game

class GameSettingsPresenterImpl(gView: GameSettingsContract.GameSettingsView,gModel:GameSettingsContract.GameSettingsModel):GameSettingsContract.GameSettingsPresenter {

    private var view: GameSettingsContract.GameSettingsView = gView
    private var model: GameSettingsContract.GameSettingsModel = gModel

    override fun getNewGame(gameType: Int): Game {
        return model.createGame(gameType)
    }

    override fun saveSetting(key: String, value: String) {
        TODO("Save the settings chosen")
    }

    override fun gameSettingsAccepted(game:Game) {
        model.saveGame(game)
    }


}