package com.onewisebit.scpescape.model.repositories

import com.onewisebit.scpescape.model.entities.Participant
import com.onewisebit.scpescape.model.entities.Round
import io.reactivex.Completable
import io.reactivex.Flowable

interface InRoundRepository {

    fun insertRound(round: Round): Completable

    fun getRounds(gameID: Long): Flowable<List<Round>>

    fun deleteGameRounds(gameID: Long): Completable

    fun getMissingParticipants(gameID: Long): List<Participant>
}