package com.onewisebit.scpescape.newgamesettings

import androidx.lifecycle.LiveData
import com.onewisebit.scpescape.model.entities.Game
import com.onewisebit.scpescape.model.entities.Mode
import io.reactivex.Single

interface GameSettingsContract {
    interface GameSettingsView

    interface GameSettingsPresenter {
        fun onNewGame(): LiveData<Long>
        fun onModeLoaded(): LiveData<Mode>
        fun createNewGame(gameMode: Int,gameType: Int)
        fun gameSettingsAccepted(game: Game)
        fun saveSetting(key: String, value: String)
        fun getMode(gameMode: Int)
    }

    interface GameSettingsModel {
        fun createGame(gameMode: Int ,gameType: Int): Single<Long>
        fun saveGame(game: Game)
        fun getMode(gameMode: Int): Single<Mode>
    }
}