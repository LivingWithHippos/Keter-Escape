package com.onewisebit.scpescape.modesList.model

import com.onewisebit.scpescape.model.ModeDataClass
import com.onewisebit.scpescape.model.repositories.InModelNewRepository
import com.onewisebit.scpescape.modesList.GameModesContract
import com.onewisebit.scpescape.utilities.MODE_DATA_FILENAME

class GameModesModelImpl(modeRepository: InModelNewRepository) : GameModesContract.GameModesModel {

    private var modeRepo: InModelNewRepository = modeRepository

    override suspend fun getModes(): List<ModeDataClass> {
        return modeRepo.getAllModes() ?: throw IllegalArgumentException("No modes could be loaded from $MODE_DATA_FILENAME")
    }
}