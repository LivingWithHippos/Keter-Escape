package com.onewisebit.scpescape.game

import com.onewisebit.scpescape.model.entities.*
import io.reactivex.Flowable
import io.reactivex.Single

interface GameContract {

    interface GameModel{
        fun getGame(gameID: Long): Single<Game>
        fun getMode(gameID: Long): Single<Mode>
        fun getParticipant(gameID: Long): Flowable<List<Participant>>
        fun getPlayers(gameID: Long): Flowable<List<Player>>
        fun getRoles(gameID: Long): Flowable<List<Role>>
    }

    interface GameView{

    }

    interface GamePresenter{
        fun setupGame(gameID: Long)
    }
}