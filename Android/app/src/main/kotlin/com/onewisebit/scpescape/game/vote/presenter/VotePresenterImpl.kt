package com.onewisebit.scpescape.game.vote.presenter

import com.onewisebit.scpescape.game.vote.VoteContract
import com.onewisebit.scpescape.game.composable.ContractAction
import com.onewisebit.scpescape.model.entities.Participant
import com.onewisebit.scpescape.model.entities.Player
import com.onewisebit.scpescape.model.entities.Vote

class VotePresenterImpl(
    val view: VoteContract.VoteView,
    val actionPresenter:ContractAction.PresenterAction,
    val gameID: Long,
    val roundCode : String,
    val roleName: String
): VoteContract.VotePresenter, ContractAction.PresenterAction by actionPresenter {

    override suspend fun loadValues() {

    }
}