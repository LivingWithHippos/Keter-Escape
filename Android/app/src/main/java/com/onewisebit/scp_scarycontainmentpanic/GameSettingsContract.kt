package com.onewisebit.scp_scarycontainmentpanic

import com.onewisebit.scp_scarycontainmentpanic.model.Game

interface GameSettingsContract {
    interface GameSettingsView{
    }

    interface GameSettingsPresenter {
        fun getNewGame(gameType: Int): Game
        fun gameSettingsAccepted(game:Game)
        fun saveSetting(key:String,value:String)
    }

    interface GameSettingsModel {
        fun createGame(gameType: Int): Game
        fun saveGame(game:Game)
    }
}