package com.onewisebit.scpescape.model.repositories

import com.onewisebit.scpescape.model.entities.Round
import com.onewisebit.scpescape.model.parsed.RoundDetails
import io.reactivex.Completable

interface InRoundRepository {

    fun insertRound(round: Round): Completable

    suspend fun getRounds(gameID: Long): List<Round>

    fun deleteGameRounds(gameID: Long): Completable

    suspend fun getGameRoundInfo(gameId: Long, roundCode: String): RoundDetails?

    suspend fun getRoundInfo(modeId: Int, roundCode: String): RoundDetails?

    suspend fun getAllGameRoundDetails(gameId: Long): List<RoundDetails>?

    suspend fun getAllModeRoundInfo(modeId: Int): List<RoundDetails>?

    suspend fun getAllRoundsDetails(): List<RoundDetails>?

    suspend fun getRoundsNumber(gameId: Long): Int
}