package com.onewisebit.scpescape.game

import com.onewisebit.scpescape.game.basemvp.ContractRound

/**
 * Generic contract with all the game data needed, is implemented by the game single contract
 */
interface GameContract {

    interface GameView

    interface GamePresenter: ContractRound.PresenterRound {
        fun onDestroy()
    }

    interface GameModel : ContractRound.ModelRound {
    }
}