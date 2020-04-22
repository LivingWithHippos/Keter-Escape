package com.onewisebit.scpescape.model.repositories

import com.onewisebit.scpescape.model.entities.Round
import com.onewisebit.scpescape.model.parsed.RoundDetails
import io.reactivex.Completable

interface InRoundRepository {

    suspend fun insertRound(round: Round): Long

    suspend fun getRounds(gameID: Long): List<Round>

    suspend fun getLastRound(gameID: Long): Round?

    fun deleteGameRounds(gameID: Long): Completable

    suspend fun getGameRoundInfo(gameId: Long, roundCode: String): RoundDetails?

    suspend fun getRoundInfo(modeId: Int, roundCode: String): RoundDetails?

    suspend fun getAllGameRoundDetails(gameId: Long): List<RoundDetails>?

    suspend fun getAllRoundsDetails(modeId: Int): List<RoundDetails>?

    suspend fun getRoundsNumber(gameId: Long): Int

    suspend fun getRoundsMode(gameID: Long): Int
}