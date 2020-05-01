package com.onewisebit.scpescape.game.composable.presenter

import com.onewisebit.scpescape.fsm.states.StateGame
import com.onewisebit.scpescape.game.composable.ContractGame
import com.onewisebit.scpescape.model.entities.Game
import com.onewisebit.scpescape.model.entities.Save

class PresenterGameImpl(val model: ContractGame.ModelGame, val gameId: Long) :
    ContractGame.PresenterGame {
    override suspend fun getGame(): Game = model.getGame(gameId)

    override suspend fun saveGameState(oldState: StateGame, newState: StateGame) {

        val oldS = oldState.getName()
        val newS = newState.getName()
        saveGame(oldS,newS)
    }

    override suspend fun getSave(): Save = model.getSave(gameId)

    override suspend fun saveGame(
        stateMachineOld: String?,
        stateMachineNew: String?,
        player: Long?,
        stateProcessed: Boolean
    ) = model.saveGame(gameId, stateMachineOld, stateMachineNew, player, stateProcessed)

    override suspend fun saveMachineStates(oldState: String, newState: String) =
        model.saveMachineStates(gameId, oldState, newState)

    override suspend fun saveCurrentPlayer(playerID: Long) =
        model.saveCurrentPlayer(gameId, playerID)

    override suspend fun saveStateProcessed(processed: Boolean) =
        model.saveStateProcessed(gameId, processed)
}