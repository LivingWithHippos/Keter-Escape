package com.onewisebit.scpescape.game.basemvp

import com.onewisebit.scpescape.model.parsed.ModeDataClass
import com.onewisebit.scpescape.model.repositories.InGameRepository
import com.onewisebit.scpescape.model.repositories.InModeJSONRepository

class ModelModeImpl(val modeRepository: InModeJSONRepository, val gameRepository: InGameRepository): ContractMode.ModelMode {
    override suspend fun getMode(gameID: Long): ModeDataClass? {
        val modeId = gameRepository.getModeId(gameID)
        return modeRepository.getMode(modeId)
    }
}