package com.onewisebit.scpescape.game.composable.presenter

import com.onewisebit.scpescape.game.composable.ContractParticipant
import com.onewisebit.scpescape.game.composable.ContractVote

class PresenterVoteImpl(
    val model: ContractVote.ModelVote,
    val gameID: Long
):ContractVote.PresenterVote {
}