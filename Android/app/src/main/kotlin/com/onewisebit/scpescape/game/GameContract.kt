package com.onewisebit.scpescape.game

import com.onewisebit.scpescape.game.basemvp.*

/**
 * Generic contract with all the game data needed, is implemented by the game single contract
 */
interface GameContract {

    interface GameView

    interface GamePresenter : ContractRound.PresenterRound, ContractTurn.PresenterTurn,
        ContractParticipant.PresenterParticipant, ContractPlayer.PresenterPlayer, ContractAction.PresenterAction {
        fun onDestroy()
        suspend fun newPlayerTurn(): String
    }

    interface GameModel : ContractRound.ModelRound, ContractTurn.ModelTurn,
        ContractParticipant.ModelParticipant, ContractPlayer.ModelPlayer,  ContractAction.ModelAction
}