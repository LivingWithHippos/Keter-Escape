package com.onewisebit.scp_scarycontainmentpanic

import com.onewisebit.scp_scarycontainmentpanic.model.Participant
import com.onewisebit.scp_scarycontainmentpanic.model.Player
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
    }

    interface PlayersModel {
        fun getAllPlayers(): Flowable<List<Player>>
        fun getPlayersByName(name: String): Flowable<List<Player>>
        fun getParticipantsIDByGame(gameID: Long): Flowable<List<Long>>
        fun getParticipantsByGame(gameID: Long): Flowable<List<Participant>>
        fun addGameParticipant(gameID: Long, playerID: Long): Completable
        fun removeGameParticipant(gameID: Long, playerID: Long): Completable
        fun getParticipantsNumber(gameID: Long): Single<Int>
    }
}