package com.onewisebit.scpescape.game.vote

import com.onewisebit.scpescape.game.composable.ContractAction
import com.onewisebit.scpescape.model.entities.Participant
import com.onewisebit.scpescape.model.entities.Player
import com.onewisebit.scpescape.model.entities.Vote

interface VoteContract {

    interface VoteModel: ContractAction.ModelAction
    interface VotePresenter: ContractAction.PresenterAction {
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