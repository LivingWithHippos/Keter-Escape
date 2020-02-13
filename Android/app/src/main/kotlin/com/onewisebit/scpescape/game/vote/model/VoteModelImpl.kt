package com.onewisebit.scpescape.game.vote.model

import com.onewisebit.scpescape.game.composable.ContractAction
import com.onewisebit.scpescape.game.composable.ContractParticipant
import com.onewisebit.scpescape.game.composable.ContractPlayer
import com.onewisebit.scpescape.game.composable.ContractVote
import com.onewisebit.scpescape.game.vote.VoteContract

class VoteModelImpl(
    val voteModel: ContractVote.ModelVote,
    val actionModel: ContractAction.ModelAction,
    val participantModel: ContractParticipant.ModelParticipant,
    val playerModel: ContractPlayer.ModelPlayer
) :
    VoteContract.VoteModel,
    ContractVote.ModelVote by voteModel,
    ContractAction.ModelAction by actionModel,
    ContractParticipant.ModelParticipant by participantModel,
    ContractPlayer.ModelPlayer by playerModel