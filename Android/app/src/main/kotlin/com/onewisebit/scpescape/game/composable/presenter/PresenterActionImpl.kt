package com.onewisebit.scpescape.game.composable.presenter

import com.onewisebit.scpescape.game.composable.ContractAction
import com.onewisebit.scpescape.model.parsed.RoleDetails
import com.onewisebit.scpescape.model.parsed.TurnAction

class PresenterActionImpl(val model: ContractAction.ModelAction, val gameId: Long) :
    ContractAction.PresenterAction {

    var modeId: Int? = null

    override suspend fun getAction(roleName: String, roundCode: String): TurnAction {
        return model.getCompleteAction(getModeId(), roleName, roundCode)
    }

    override suspend fun getRoleDetails(roleName: String): RoleDetails {
        return model.getRoleDetails(getModeId(), roleName)
    }

    override suspend fun getPartialAction(roleName: String, roundName: String): TurnAction {
        return model.getPartialAction(getModeId(), roleName, roundName)
    }

    override suspend fun getModeActions(): List<TurnAction> {
        return model.getModeActions(getModeId())
    }

    private suspend fun getModeId(): Int {
        if (modeId == null)
            modeId = model.getModeId(gameId)
        return modeId as Int
    }

}