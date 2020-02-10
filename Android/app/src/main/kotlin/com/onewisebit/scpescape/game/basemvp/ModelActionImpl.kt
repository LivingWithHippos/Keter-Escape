package com.onewisebit.scpescape.game.basemvp

import com.onewisebit.scpescape.model.parsed.TurnAction
import com.onewisebit.scpescape.model.repositories.InTurnActionRepository

class ModelActionImpl(
    val actionRepository: InTurnActionRepository
): ContractAction.ModelAction {

    override suspend fun getTemplates(): List<TurnAction> = actionRepository.getTemplates()

    override suspend fun getRoleAction(
        modeId: Int,
        roleName: String,
        roundCode: String
    ): TurnAction = actionRepository.getRoleAction(modeId,roleName,roundCode)

    override suspend fun getModeActions(modeId: Int): List<TurnAction> = actionRepository.getModeActions(modeId)

}