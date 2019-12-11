package com.onewisebit.scpescape.newgamesettings

import com.onewisebit.scpescape.model.entities.Game
import com.onewisebit.scpescape.model.entities.Mode
import io.reactivex.Single

interface GameSettingsContract {
    interface GameSettingsView

    interface GameSettingsPresenter {
        fun getNewGame(gameMode: Int,gameType: Int): Single<Long>
        fun gameSettingsAccepted(game: Game)
        fun saveSetting(key: String, value: String)
        fun getMode(gameMode: Int): Single<Mode>
    }

    interface GameSettingsModel {
        fun createGame(gameMode: Int ,gameType: Int): Single<Long>
        fun saveGame(game: Game)
        fun getMode(gameMode: Int): Single<Mode>
    }
}