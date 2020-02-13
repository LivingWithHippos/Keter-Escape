package com.onewisebit.scpescape.game.composable.presenter

import com.onewisebit.scpescape.game.composable.ContractAction
import com.onewisebit.scpescape.model.parsed.RoleDetails
import com.onewisebit.scpescape.model.parsed.TurnAction

class PresenterActionImpl(val model: ContractAction.ModelAction, val gameId: Long) :
    ContractAction.PresenterAction {

    override suspend fun getAction(roleName: String, roundCode: String): TurnAction {
        val modeId = model.getModeId(gameId)
        return model.getCompleteAction(modeId, roleName, roundCode)
    }

    override suspend fun getRoleDetails(roleName: String): RoleDetails {
        val modeId = model.getModeId(gameId)
        return model.getRoleDetails(modeId, roleName)
    }

    override suspend fun getPartialAction(roleName: String, roundName: String): TurnAction {
        val modeId = model.getModeId(gameId)
        return model.getPartialAction(modeId, roleName, roundName)
    }

}