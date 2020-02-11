package com.onewisebit.scpescape.game

import com.onewisebit.scpescape.game.composable.ContractAction

interface VoteContract {

    interface VoteModel: ContractAction.ModelAction
    interface VotePresenter: ContractAction.PresenterAction
    interface VoteView
}