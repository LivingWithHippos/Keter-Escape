package com.onewisebit.scp_scarycontainmentpanic.model

import io.reactivex.Completable
import io.reactivex.Flowable

interface InParticipantRepository {
    fun getParticipant(gameID: Long, playerID: Long): Participant
    fun getGameParticipants(gameID: Long): Flowable<List<Participant>>
    fun getGameParticipantsID(gameID: Long): Flowable<List<Long>>
    fun getParticipantState(gameID: Long, playerID: Long): Int
    fun insertParticipant(participant: Participant): Completable
    fun updateParticipant(participant: Participant)
    fun removeParticipant(participant: Participant)
    fun removeParticipant(gameID: Long, playerID: Long)
    fun removeAllParticipant()

}