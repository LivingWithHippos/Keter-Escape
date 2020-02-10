package com.onewisebit.scpescape.game.basemvp

import com.onewisebit.scpescape.model.parsed.TurnAction

class PresenterActionImpl(val model: ContractAction.ModelAction, val gameId: Long) :
    ContractAction.PresenterAction {

    override suspend fun getRoleAction(roleName: String, roundCode: String): TurnAction {
        val modeId = model.getModeId(gameId)
        return model.getRoleAction(modeId, roleName, roundCode)
    }

}