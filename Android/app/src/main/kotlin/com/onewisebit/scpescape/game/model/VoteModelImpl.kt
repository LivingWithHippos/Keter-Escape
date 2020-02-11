package com.onewisebit.scpescape.game.model

import com.onewisebit.scpescape.game.VoteContract
import com.onewisebit.scpescape.game.composable.ContractAction

class VoteModelImpl(
    val actionModel : ContractAction.ModelAction
): VoteContract.VoteModel, ContractAction.ModelAction by actionModel {
}