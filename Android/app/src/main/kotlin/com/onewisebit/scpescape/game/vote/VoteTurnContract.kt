package com.onewisebit.scpescape.game.vote

import com.onewisebit.scpescape.game.composable.ContractAction
import com.onewisebit.scpescape.game.composable.ContractParticipant
import com.onewisebit.scpescape.game.composable.ContractPlayer
import com.onewisebit.scpescape.game.composable.ContractVote
import com.onewisebit.scpescape.model.parsed.VoteParticipant

interface VoteTurnContract {

    interface VoteTurnModel : ContractVote.ModelVote, ContractAction.ModelAction,
        ContractParticipant.ModelParticipant, ContractPlayer.ModelPlayer

    interface VoteTurnPresenter : ContractVote.PresenterVote, ContractAction.PresenterAction,
        ContractParticipant.PresenterParticipant, ContractPlayer.PresenterPlayer {
        suspend fun loadValues(roleName: String, roundCode: String)
        suspend fun setCurrentTurnVote(votedPlayerId: Long): Boolean
        suspend fun checkVotes()
    }

    interface VoteTurnView {
        fun setFab(visible: Boolean)
        fun initializeList(voteParticipants: List<VoteParticipant>)
    }
}