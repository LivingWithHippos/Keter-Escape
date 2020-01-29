package com.onewisebit.scpescape.newgamesettings

import androidx.lifecycle.LiveData
import com.onewisebit.scpescape.model.parsed.ModeDataClass
import com.onewisebit.scpescape.model.entities.Game
import io.reactivex.Single

interface GameSettingsContract {
    interface GameSettingsView

    interface GameSettingsPresenter {
        fun onNewGame(): LiveData<Long>
        suspend fun getMode(id: Int): ModeDataClass?
        fun createNewGame(gameMode: Int, gameType: Int)
        fun gameSettingsAccepted(game: Game)
        fun saveSetting(key: String, value: String)
    }

    interface GameSettingsModel {
        fun createGame(gameMode: Int, gameType: Int): Single<Long>
        fun saveGame(game: Game)
        suspend fun getMode(gameMode: Int): ModeDataClass?
    }
}