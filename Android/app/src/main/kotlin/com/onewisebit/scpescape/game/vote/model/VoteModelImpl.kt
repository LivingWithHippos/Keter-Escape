package com.onewisebit.scpescape.game.vote.model

import com.onewisebit.scpescape.game.vote.VoteContract
import com.onewisebit.scpescape.game.composable.ContractAction

class VoteModelImpl(
    val actionModel : ContractAction.ModelAction
): VoteContract.VoteModel, ContractAction.ModelAction by actionModel {
}