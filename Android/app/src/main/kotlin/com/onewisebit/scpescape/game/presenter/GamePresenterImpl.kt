package com.onewisebit.scpescape.game.presenter

import com.onewisebit.scpescape.game.GameContract
import com.onewisebit.scpescape.game.basemvp.*

open class GamePresenterImpl(
    val gameView: GameContract.GameView,
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
    ContractAction.PresenterAction by actionPresenter
{

    override fun onDestroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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