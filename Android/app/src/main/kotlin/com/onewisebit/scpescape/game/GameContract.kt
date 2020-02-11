package com.onewisebit.scpescape.game

import com.onewisebit.scpescape.game.composable.*

/**
 * Generic contract with all the game data needed, is implemented by the game single contract
 */
interface GameContract {

    interface GameView {
        fun showPlayerTurnFragment(name: String, role: String, description: String)
        fun showPlayerVoteFragment()
        fun showPlayerInfoFragment(title: String, description: String)
    }

    interface GamePresenter : ContractRound.PresenterRound, ContractTurn.PresenterTurn,
        ContractParticipant.PresenterParticipant, ContractPlayer.PresenterPlayer, ContractAction.PresenterAction {
        fun onDestroy()
        suspend fun setupPlayerTurnFragment()
        suspend fun setupPlayerPowerFragment()
        suspend fun newPlayerTurn(): String
    }

    interface GameModel : ContractRound.ModelRound, ContractTurn.ModelTurn,
        ContractParticipant.ModelParticipant, ContractPlayer.ModelPlayer,  ContractAction.ModelAction
}