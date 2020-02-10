package com.onewisebit.scpescape.modesList.model

import com.onewisebit.scpescape.model.parsed.ModeDataClass
import com.onewisebit.scpescape.model.repositories.InModeJSONRepository
import com.onewisebit.scpescape.modesList.GameModesContract

class GameModesModelImpl(modeRepository: InModeJSONRepository) : GameModesContract.GameModesModel {

    private var modeRepo: InModeJSONRepository = modeRepository

    //todo: check error in called method
    override suspend fun getModes(): List<ModeDataClass> {
        return modeRepo.getAllModes()
            ?: throw IllegalArgumentException("No modes could be loaded")
    }
}