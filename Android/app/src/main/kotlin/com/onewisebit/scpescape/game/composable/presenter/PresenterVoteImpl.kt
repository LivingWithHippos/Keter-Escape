package com.onewisebit.scpescape.game.composable.presenter

import com.onewisebit.scpescape.game.composable.ContractParticipant
import com.onewisebit.scpescape.game.composable.ContractVote
import com.onewisebit.scpescape.model.entities.Player
import com.onewisebit.scpescape.model.entities.Vote

class PresenterVoteImpl(
    val model: ContractVote.ModelVote,
    val gameID: Long
):ContractVote.PresenterVote {

    override suspend fun getLastRoundVotes(): List<Vote> =
        model.getLastRoundVotes(gameID)
}