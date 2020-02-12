package com.onewisebit.scpescape.game.vote

import com.onewisebit.scpescape.game.composable.ContractAction
import com.onewisebit.scpescape.game.composable.ContractParticipant
import com.onewisebit.scpescape.game.composable.ContractVote
import com.onewisebit.scpescape.model.entities.Participant
import com.onewisebit.scpescape.model.entities.Player
import com.onewisebit.scpescape.model.entities.Vote

interface VoteContract {

    interface VoteModel: ContractVote.ModelVote, ContractAction.ModelAction, ContractParticipant.ModelParticipant
    interface VotePresenter: ContractVote.PresenterVote, ContractAction.PresenterAction, ContractParticipant.PresenterParticipant {
        suspend fun loadValues()
    }

    interface VoteView {
        fun enableFab()
        fun updateList(
            playersList: List<Player>,
            participantsList: List<Participant>?,
            votesList: List<Vote>?,
            votedPlayersList: List<Player>?,
            enabledPlayersList: List<Long>?
        )
    }
}