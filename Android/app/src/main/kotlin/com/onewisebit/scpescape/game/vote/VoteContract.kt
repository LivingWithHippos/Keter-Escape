package com.onewisebit.scpescape.game.vote

import com.onewisebit.scpescape.game.composable.ContractAction
import com.onewisebit.scpescape.game.composable.ContractParticipant
import com.onewisebit.scpescape.game.composable.ContractPlayer
import com.onewisebit.scpescape.game.composable.ContractVote
import com.onewisebit.scpescape.model.parsed.VoteParticipant

interface VoteContract {

    interface VoteModel : ContractVote.ModelVote, ContractAction.ModelAction,
        ContractParticipant.ModelParticipant, ContractPlayer.ModelPlayer

    interface VotePresenter : ContractVote.PresenterVote, ContractAction.PresenterAction,
        ContractParticipant.PresenterParticipant, ContractPlayer.PresenterPlayer {
        suspend fun loadValues(roleName: String, roundCode: String)
    }

    interface VoteView {
        fun enableFab()
        fun updateList(voteParticipants: List<VoteParticipant>)
    }
}