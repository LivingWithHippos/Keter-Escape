package com.onewisebit.scpescape.main.loadgames.model

import com.onewisebit.scpescape.main.loadgames.GamesListContract
import com.onewisebit.scpescape.model.entities.Game
import com.onewisebit.scpescape.model.entities.Player
import com.onewisebit.scpescape.model.repositories.InGameRepository
import io.reactivex.Flowable

class GamesListModelImpl (
    gameRepository: InGameRepository
) : GamesListContract.GamesListModel {

    private var gameRepo: InGameRepository = gameRepository

    override fun getUnfinishedGamesList(): Flowable<List<Game>> =
        gameRepo.getUnfinishedGames()

    override fun getGamesListByName(name: String): Flowable<List<Player>> {
        TODO("Not yet implemented")
    }

    override fun deleteGameByID(gameID: Long) {
        TODO("Not yet implemented")
    }
}