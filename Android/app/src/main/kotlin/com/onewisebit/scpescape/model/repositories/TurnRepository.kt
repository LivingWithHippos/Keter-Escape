package com.onewisebit.scpescape.model.repositories

import com.onewisebit.scpescape.model.daos.TurnDAO
import com.onewisebit.scpescape.model.entities.Turn
import io.reactivex.Completable
import io.reactivex.Flowable

class TurnRepository(private val turnDAO: TurnDAO) : InTurnRepository {

    override fun insertTurn(turn: Turn): Completable = turnDAO.insertTurn(turn)

    override fun getGameTurns(gameID: Long): Flowable<List<Turn>> = turnDAO.getGameTurns(gameID)

    override fun getPlayerTurns(gameID: Long, playerID: Long): Flowable<List<Turn>> =
        turnDAO.getPlayerTurns(gameID, playerID)

    override fun getRoundTurns(gameID: Long, roundNumber: Int): Flowable<List<Turn>> =
        turnDAO.getRoundTurns(gameID, roundNumber)

    override fun deleteGameTurns(gameID: Long): Completable = turnDAO.deleteGameTurns(gameID)
}