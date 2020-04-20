package com.onewisebit.scpescape.game.composable.model

import com.onewisebit.scpescape.game.composable.ContractMode
import com.onewisebit.scpescape.model.parsed.ModeDataClass
import com.onewisebit.scpescape.model.parsed.VictoryCondition
import com.onewisebit.scpescape.model.repositories.InGameRepository
import com.onewisebit.scpescape.model.repositories.InModeJSONRepository
import com.onewisebit.scpescape.model.repositories.InVictoryConditionsRepository

class ModelModeImpl(
    val modeRepository: InModeJSONRepository,
    val gameRepository: InGameRepository,
    val victoryRepository: InVictoryConditionsRepository
) : ContractMode.ModelMode {
    override suspend fun getMode(gameID: Long): ModeDataClass? {
        val modeId = gameRepository.getModeId(gameID)
        return modeRepository.getMode(modeId)
    }

    override suspend fun getVictoryConditions(gameID: Long): List<VictoryCondition> {
        val modeId = gameRepository.getModeId(gameID)
        return victoryRepository.getVictoryConditions(modeId)
    }
}