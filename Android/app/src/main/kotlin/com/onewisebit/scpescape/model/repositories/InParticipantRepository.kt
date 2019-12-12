package com.onewisebit.scpescape.model.repositories

import com.onewisebit.scpescape.model.entities.Participant
import com.onewisebit.scpescape.model.entities.Role
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface InParticipantRepository {
    fun getParticipant(gameID: Long, playerID: Long): Participant
    fun getGameParticipants(gameID: Long): Flowable<List<Participant>>
    fun getGameParticipantsID(gameID: Long): Flowable<List<Long>>
    fun getParticipantState(gameID: Long, playerID: Long): Int
    fun getParticipantRole(gameID: Long, playerID: Long): Single<Role>
    fun insertParticipant(participant: Participant): Completable
    fun updateParticipant(participant: Participant)
    fun removeParticipant(participant: Participant): Completable
    fun removeParticipant(gameID: Long, playerID: Long): Completable
    fun removeAllParticipant()
    fun getParticipantNumber(gameID: Long): Single<Int>

}