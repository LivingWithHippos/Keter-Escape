package com.onewisebit.scpescape.model.repositories

import com.onewisebit.scpescape.model.daos.RoundDAO
import com.onewisebit.scpescape.model.entities.Round
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class RoundRepository(private val roundDAO: RoundDAO): InRoundRepository {

    override fun insertRound(round: Round): Completable = roundDAO.insertRound(round)

    override fun getRounds(gameID: Long): Flowable<List<Round>> = roundDAO.getRounds(gameID)

    override fun deleteGameRounds(gameID: Long): Completable = roundDAO.deleteGameRounds(gameID)
}