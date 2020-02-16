package com.onewisebit.scpescape.game.activity.presenter

import com.onewisebit.scpescape.game.activity.GameContract
import com.onewisebit.scpescape.game.composable.*
import com.onewisebit.scpescape.model.parsed.InfoSettings
import com.onewisebit.scpescape.model.parsed.VoteSettings
import com.onewisebit.scpescape.utilities.POWER_INFO
import com.onewisebit.scpescape.utilities.POWER_VOTE

open class GamePresenterImpl(
    var gameView: GameContract.GameView,
    val gameModel: GameContract.GameModel,
    val roundPresenter: ContractRound.PresenterRound,
    val turnPresenter: ContractTurn.PresenterTurn,
    val participantPresenter: ContractParticipant.PresenterParticipant,
    val playerPresenter: ContractPlayer.PresenterPlayer,
    val actionPresenter: ContractAction.PresenterAction,
    val gameID: Long
) : GameContract.GamePresenter,
    ContractRound.PresenterRound by roundPresenter,
    ContractTurn.PresenterTurn by turnPresenter,
    ContractParticipant.PresenterParticipant by participantPresenter,
    ContractPlayer.PresenterPlayer by playerPresenter,
    ContractAction.PresenterAction by actionPresenter {

    override fun onDestroy() {
    }

    override suspend fun setupPlayerTurnFragment() {

        val playerId = turnPresenter.getLatestTurn().playerID
        val playerName = playerPresenter.getPlayer(playerId).name
        val roleName: String = participantPresenter.getParticipant(playerId).roleName!!
        val roundName = roundPresenter.getCurrentRound().details
        val actionDescription = actionPresenter.getPartialAction(roleName, roundName).description

        gameView.showPlayerTurnFragment(playerName, roleName, actionDescription)
    }

    override suspend fun setupPlayerPowerFragment() {

        val playerId = turnPresenter.getLatestTurn().playerID
        val roleName: String = participantPresenter.getParticipant(playerId).roleName!!
        val roundName = roundPresenter.getCurrentRound().details
        val action = actionPresenter.getAction(roleName, roundName)
        val lastTurn: Boolean = turnPresenter.isLastTurn()

        when (action.extends) {
            POWER_VOTE -> {
                (action as VoteSettings).run {
                    gameView.showPlayerVoteFragment(roundName, roleName, lastTurn)
                }
            }
            POWER_INFO -> (action as InfoSettings).run {
                gameView.showPlayerInfoFragment(
                    this.information!!.title!!,
                    this.information!!.description!!,
                    lastTurn
                )
            }
            else -> throw IllegalArgumentException("No action found to extend: ${action.extends}")
        }

    }

    override suspend fun setupRoundResultsFragment() {
        gameView.showRoundResultFragment()
    }

    override suspend fun newPlayerTurn(): String {
        // if this is null either the game is finished and no more turn should have been created
        // or there was a problem retrieving participants
        val missingParticipants: List<Long> = gameModel.getMissingTurnsParticipants(gameID)!!
        // this is supposing the round is created before the turns
        val playerID = missingParticipants.random()
        gameModel.addTurn(gameID, playerID)
        //load and return player name
        return gameModel.getPlayerName(playerID)
            ?: throw IllegalArgumentException("No player's name found for player id $playerID")
    }
}