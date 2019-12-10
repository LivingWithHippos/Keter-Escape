package com.onewisebit.scp_scarycontainmentpanic

import com.onewisebit.scp_scarycontainmentpanic.model.Player
import io.reactivex.Completable
import io.reactivex.Flowable

interface PlayersContract {

    interface PlayersView {
        fun initView(players: Flowable<List<Player>>, participants: Flowable<List<Long>>)
    }

    interface PlayersPresenter {
        fun setPlayers(gameID:Long)
        fun addParticipant(gameID: Long, playerID: Long): Completable
        fun removeParticipant(gameID: Long, playerID: Long)
    }

    interface PlayersModel {
        fun getAllPlayers(): Flowable<List<Player>>
        fun getPlayersByName(name: String): Flowable<List<Player>>
        fun getParticipantsByGame(gameID: Long): Flowable<List<Long>>
        fun addGameParticipant(gameID: Long, playerID: Long): Completable
        fun removeGameParticipant(gameID: Long, playerID: Long)
    }
}