package com.onewisebit.scpescape.main.loadgames

import androidx.lifecycle.LiveData
import com.onewisebit.scpescape.model.entities.Game
import com.onewisebit.scpescape.model.entities.Player
import io.reactivex.Flowable

interface GamesListContract {
    
    interface GamesListView {
        fun initView(games: LiveData<List<Game>>)
    }

    interface GamesListPresenter {
        fun initializeGamesList()
        fun deleteGame(gameID: Long)
    }

    interface GamesListModel {
        fun getUnfinishedGamesList(): Flowable<List<Game>>
        fun getGamesListByName(name: String): Flowable<List<Player>>
        fun deleteGameByID(gameID: Long)
    }
}