package com.onewisebit.scpescape.newgamesettings

import com.onewisebit.scpescape.model.entities.Game
import io.reactivex.Single

interface GameSettingsContract {
    interface GameSettingsView

    interface GameSettingsPresenter {
        fun getNewGame(gameType: Int): Single<Long>
        fun gameSettingsAccepted(game: Game)
        fun saveSetting(key: String, value: String)
    }

    interface GameSettingsModel {
        fun createGame(gameType: Int): Single<Long>
        fun saveGame(game: Game)
    }
}