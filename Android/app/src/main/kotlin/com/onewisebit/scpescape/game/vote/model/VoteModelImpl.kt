package com.onewisebit.scpescape.game.vote.model

import com.onewisebit.scpescape.game.vote.VoteContract
import com.onewisebit.scpescape.game.composable.ContractAction
import com.onewisebit.scpescape.game.composable.ContractParticipant
import com.onewisebit.scpescape.game.composable.ContractVote

class VoteModelImpl(
    val voteModel : ContractVote.ModelVote,
    val actionModel : ContractAction.ModelAction,
    val participantModel : ContractParticipant.ModelParticipant
): VoteContract.VoteModel,
ContractVote.ModelVote by voteModel,
ContractAction.ModelAction by actionModel,
ContractParticipant.ModelParticipant by participantModel{
}