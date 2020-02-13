package com.onewisebit.scpescape.main.modesList.model

import com.onewisebit.scpescape.main.modesList.GameModesContract
import com.onewisebit.scpescape.model.parsed.ModeDataClass
import com.onewisebit.scpescape.model.repositories.InModeJSONRepository

class GameModesModelImpl(modeRepository: InModeJSONRepository) : GameModesContract.GameModesModel {

    private var modeRepo: InModeJSONRepository = modeRepository

    //todo: check error in called method
    override suspend fun getModes(): List<ModeDataClass> {
        return modeRepo.getAllModes()
            ?: throw IllegalArgumentException("No modes could be loaded")
    }
}