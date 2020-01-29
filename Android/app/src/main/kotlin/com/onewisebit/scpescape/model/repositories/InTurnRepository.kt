package com.onewisebit.scpescape.model.repositories

import com.onewisebit.scpescape.model.entities.Participant
import com.onewisebit.scpescape.model.entities.Turn
import io.reactivex.Completable
import io.reactivex.Flowable

interface InTurnRepository {

    fun insertTurn(turn: Turn): Completable

    suspend fun getGameTurns(gameID: Long): List<Turn>

    fun getPlayerTurns(gameID: Long, playerID: Long): Flowable<List<Turn>>

    fun getRoundTurns(gameID: Long, roundNumber: Int): Flowable<List<Turn>>

    fun deleteGameTurns(gameID: Long): Completable

    suspend fun getCurrentParticipant(gameID: Long): Participant
}