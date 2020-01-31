package com.onewisebit.scpescape.model.repositories

import com.onewisebit.scpescape.model.entities.Round
import com.onewisebit.scpescape.model.parsed.RoundDetails
import com.onewisebit.scpescape.model.parsed.RoundInformation
import io.reactivex.Completable

interface InRoundRepository {

    fun insertRound(round: Round): Completable

    suspend fun getRounds(gameID: Long): List<Round>

    fun deleteGameRounds(gameID: Long): Completable

    suspend fun getRoundDetail(modeId: Int, roundCode: String): RoundInformation?

    suspend fun getAllDetails(modeId: Int): List<RoundInformation>?

    suspend fun getAllRoundsDetails(): List<RoundDetails>?
}