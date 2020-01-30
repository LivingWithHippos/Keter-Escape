package com.onewisebit.scpescape.model.repositories

import com.onewisebit.scpescape.model.daos.TurnDAO
import com.onewisebit.scpescape.model.entities.Participant
import com.onewisebit.scpescape.model.entities.Turn
import io.reactivex.Completable
import io.reactivex.Flowable

class TurnRepository(private val turnDAO: TurnDAO) : InTurnRepository {

    override fun insertTurn(turn: Turn): Completable = turnDAO.insertTurn(turn)

    override suspend fun getGameTurns(gameID: Long): List<Turn> = turnDAO.getGameTurns(gameID)

    override fun getPlayerTurns(gameID: Long, playerID: Long): Flowable<List<Turn>> =
        turnDAO.getPlayerTurns(gameID, playerID)

    override suspend fun getRoundTurns(gameID: Long, roundNumber: Int): List<Turn> =
        turnDAO.getRoundTurns(gameID, roundNumber)

    override fun deleteGameTurns(gameID: Long): Completable = turnDAO.deleteGameTurns(gameID)

    override suspend fun getCurrentParticipant(gameID: Long): Participant = turnDAO.getCurrentParticipant(gameID)
}