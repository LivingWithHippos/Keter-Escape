package com.onewisebit.scp_scarycontainmentpanic.model

import io.reactivex.Flowable

interface InParticipantRepository {
    fun getParticipant(gameID: Long, playerID: Long): Participant
    fun getGameParticipants(gameID: Long): Flowable<List<Participant>>
    fun getParticipantState(gameID: Long, playerID: Long): Int
    fun insertParticipant(participant: Participant)
    fun updateParticipant(participant: Participant)
    fun removeParticipant(participant: Participant)
    fun removeAllParticipant()

}