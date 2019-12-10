package com.onewisebit.scpescape.newgamesettings.presenter

import com.onewisebit.scpescape.newgamesettings.GameSettingsContract
import com.onewisebit.scpescape.model.entities.Game
import io.reactivex.Single

class GameSettingsPresenterImpl(
    gView: GameSettingsContract.GameSettingsView,
    gModel: GameSettingsContract.GameSettingsModel
) : GameSettingsContract.GameSettingsPresenter {

    private var view: GameSettingsContract.GameSettingsView = gView
    private var model: GameSettingsContract.GameSettingsModel = gModel

    override fun getNewGame(gameType: Int): Single<Long> {
        return model.createGame(gameType)
    }

    override fun saveSetting(key: String, value: String) {
        TODO("Save the settings chosen")
    }

    override fun gameSettingsAccepted(game: Game) {
        model.saveGame(game)
    }


}