package com.onewisebit.scpescape.playerslist

import com.onewisebit.scpescape.model.entities.Participant
import com.onewisebit.scpescape.model.entities.Player
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface PlayersContract {

    interface PlayersView {
        fun initView(players: Flowable<List<Player>>, participants: Flowable<List<Long>>)
    }

    interface PlayersPresenter {
        fun setPlayers(gameID: Long)
        fun addParticipant(gameID: Long, playerID: Long): Completable
        fun removeParticipant(gameID: Long, playerID: Long): Completable
        fun getParticipants(gameID: Long): Flowable<List<Participant>>
        fun getParticipantsNumber(gameID: Long): Single<Int>
        fun setGameTemporary(gameID: Long, isTemp: Boolean): Completable
    }

    interface PlayersModel {
        fun getAllPlayers(): Flowable<List<Player>>
        fun getPlayersByName(name: String): Flowable<List<Player>>
        fun getParticipantsIDByGame(gameID: Long): Flowable<List<Long>>
        fun getParticipantsByGame(gameID: Long): Flowable<List<Participant>>
        fun addGameParticipant(gameID: Long, playerID: Long): Completable
        fun removeGameParticipant(gameID: Long, playerID: Long): Completable
        fun getParticipantsNumber(gameID: Long): Single<Int>
        fun setTemporary(gameID: Long, isTemp: Boolean): Completable
    }
}