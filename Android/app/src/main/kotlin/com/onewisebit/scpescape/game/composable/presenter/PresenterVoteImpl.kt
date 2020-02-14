package com.onewisebit.scpescape.game.composable.presenter

import com.onewisebit.scpescape.game.composable.ContractVote
import com.onewisebit.scpescape.model.entities.Vote

class PresenterVoteImpl(
    val model: ContractVote.ModelVote,
    val gameID: Long
) : ContractVote.PresenterVote {

    override suspend fun getLastRoundVotes(): List<Vote> =
        model.getLastRoundVotes(gameID)

    override suspend fun getCurrentPlayerVotes(): List<Vote> =
        model.getCurrentTurnVotes(gameID)
}