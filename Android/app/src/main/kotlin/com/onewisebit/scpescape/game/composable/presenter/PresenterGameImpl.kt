package com.onewisebit.scpescape.game.composable.presenter

import com.onewisebit.scpescape.fsm.states.StateGame
import com.onewisebit.scpescape.game.composable.ContractGame
import com.onewisebit.scpescape.model.entities.Game

class PresenterGameImpl(val model: ContractGame.ModelGame, val gameId: Long) :
    ContractGame.PresenterGame {
    override suspend fun getGame(): Game = model.getGame(gameId)
    override suspend fun saveGameState(oldState: StateGame, newState: StateGame) {
        val oldS = oldState::class::simpleName.toString()
        val newS = newState::class::simpleName.toString()
        model.setGameStates(gameId, oldS, newS)
    }
}