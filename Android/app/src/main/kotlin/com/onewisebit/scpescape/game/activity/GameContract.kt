package com.onewisebit.scpescape.game.activity

import com.onewisebit.scpescape.fsm.states.StateGame
import com.onewisebit.scpescape.game.composable.*

/**
 * Generic contract with all the game data needed, is implemented by the game single contract
 */
interface GameContract {

    interface GameView {
        fun showPlayerTurnFragment(name: String, role: String, description: String)
        fun showPlayerVoteFragment(round: String, role: String, isLastTurn: Boolean = false)
        fun showPlayerInfoFragment(title: String, description: String, isLastTurn: Boolean = false)
        fun showRoundResultFragment(roundMessage: List<String>, replayRound: Boolean)
        fun nextRound()
        suspend fun loadGameState(oldState: StateGame, newState: StateGame)
        fun endGame(winner: String, message: String)
    }

    interface GamePresenter : ContractRound.PresenterRound, ContractTurn.PresenterTurn,
        ContractParticipant.PresenterParticipant, ContractPlayer.PresenterPlayer,
        ContractAction.PresenterAction, ContractVote.PresenterVote, ContractGame.PresenterGame {
        fun onDestroy()
        suspend fun setupPlayerTurnFragment()
        suspend fun setupPlayerPowerFragment()
        suspend fun newPlayerTurn(): String
        suspend fun setupRoundResultsFragment()
        suspend fun checkVictory()
        suspend fun loadGame()
    }

    interface GameModel : ContractRound.ModelRound, ContractTurn.ModelTurn,
        ContractParticipant.ModelParticipant, ContractPlayer.ModelPlayer,
        ContractAction.ModelAction, ContractVote.ModelVote, ContractGame.ModelGame
}