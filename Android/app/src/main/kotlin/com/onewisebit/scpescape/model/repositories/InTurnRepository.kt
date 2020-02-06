package com.onewisebit.scpescape.model.repositories

import com.onewisebit.scpescape.model.entities.Turn
import io.reactivex.Completable
import io.reactivex.Flowable

interface InTurnRepository {

    suspend fun insertTurn(turn: Turn)

    suspend fun getGameTurns(gameID: Long): List<Turn>

    suspend fun getLastTurn(gameID: Long): Turn?

    suspend fun getLastRoundTurn(gameID: Long): Turn?

    fun getPlayerTurns(gameID: Long, playerID: Long): Flowable<List<Turn>>

    suspend fun getRoundTurns(gameID: Long, roundNumber: Int): List<Turn>?

    suspend fun getLatestRoundTurns(gameID: Long): List<Turn>?

    suspend fun getMissingRoundPlayers(gameID: Long): List<Long>?

    fun deleteGameTurns(gameID: Long): Completable
}