package com.onewisebit.scpescape.game.basemvp

import com.onewisebit.scpescape.model.entities.Game

class PresenterGameImpl(val model: ContractGame.ModelGame, val gameId: Long) :
    ContractGame.PresenterGame {
    override suspend fun getGame(): Game = model.getGame(gameId)
}