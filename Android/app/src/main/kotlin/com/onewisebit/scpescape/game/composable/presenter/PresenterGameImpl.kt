package com.onewisebit.scpescape.game.composable.presenter

import com.onewisebit.scpescape.game.composable.ContractGame
import com.onewisebit.scpescape.model.entities.Game

class PresenterGameImpl(val model: ContractGame.ModelGame, val gameId: Long) :
    ContractGame.PresenterGame {
    override suspend fun getGame(): Game = model.getGame(gameId)
}