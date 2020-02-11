package com.onewisebit.scpescape.game.vote

import com.onewisebit.scpescape.game.composable.ContractAction

interface VoteContract {

    interface VoteModel: ContractAction.ModelAction
    interface VotePresenter: ContractAction.PresenterAction
    interface VoteView
}