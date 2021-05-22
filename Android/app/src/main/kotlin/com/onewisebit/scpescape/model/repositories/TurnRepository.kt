package com.onewisebit.scpescape.model.repositories

import com.onewisebit.scpescape.model.daos.TurnDAO
import com.onewisebit.scpescape.model.entities.Turn
import io.reactivex.Completable
import io.reactivex.Flowable

class TurnRepository(private val turnDAO: TurnDAO) : InTurnRepository {

    override suspend fun insertTurn(turn: Turn) = turnDAO.insertTurn(turn)

    override suspend fun getGameTurns(gameID: Long): List<Turn> = turnDAO.getGameTurns(gameID)

    override suspend fun getLastTurn(gameID: Long): Turn? =
        turnDAO.getLastTurn(gameID)

    override suspend fun getLastRoundTurn(gameID: Long): Turn? {
        return turnDAO.getLatestRoundTurns(gameID)?.maxByOrNull { it.turnNumber }
    }

    override fun getPlayerTurns(gameID: Long, playerID: Long): Flowable<List<Turn>> =
        turnDAO.getPlayerTurns(gameID, playerID)

    override suspend fun getRoundTurns(gameID: Long, roundNumber: Int): List<Turn>? =
        turnDAO.getRoundTurns(gameID, roundNumber)

    override suspend fun getLatestRoundTurns(gameID: Long): List<Turn>? =
        turnDAO.getLatestRoundTurns(gameID)

    override suspend fun getMissingRoundPlayers(gameID: Long): List<Long>? =
        turnDAO.getLatestRoundMissingPlayers(gameID)

    override fun deleteGameTurns(gameID: Long): Completable = turnDAO.deleteGameTurns(gameID)
}