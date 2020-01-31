package com.onewisebit.scpescape.game

/**
 * Generic contract with all the game data needed, is implemented by the game single contract
 */
interface GameContract {

    interface GameView

    interface GamePresenter {
        fun onDestroy()
    }

    interface GameModel {
    }
}