package com.onewisebit.scpescape.game

interface IntroContract {

    interface IntroModel: GameContract.GameModel{
        suspend fun assignRole(gameID: Long, playerID: Long, roleName : String)
    }

    interface IntroView : GameContract.GameView

    interface IntroPresenter : GameContract.GamePresenter {
        suspend fun assignRoles()
    }
}