package com.onewisebit.scpescape.model.repositories

import com.onewisebit.scpescape.model.entities.Participant
import com.onewisebit.scpescape.model.entities.Round
import io.reactivex.Completable
import io.reactivex.Flowable

interface InRoundRepository {

    fun insertRound(round: Round): Completable

    suspend fun getRounds(gameID: Long): List<Round>

    fun deleteGameRounds(gameID: Long): Completable
}