package com.onewisebit.scpescape.game.presenter

import com.onewisebit.scpescape.game.VoteContract
import com.onewisebit.scpescape.game.composable.ContractAction

class VotePresenterImpl(
    val view: VoteContract.VoteView,
    val actionPresenter:ContractAction.PresenterAction,
    val gameID: Long
): VoteContract.VotePresenter, ContractAction.PresenterAction by actionPresenter {
}