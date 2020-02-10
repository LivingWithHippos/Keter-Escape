package com.onewisebit.scpescape.game.basemvp

import com.onewisebit.scpescape.model.parsed.TurnAction

class PresenterActionImpl(val model: ContractAction.ModelAction, val modeId: Int) :
    ContractAction.PresenterAction {

    override suspend fun getRoleAction(roleName: String, roundCode: String): TurnAction =
        model.getRoleAction(modeId, roleName, roundCode)

}