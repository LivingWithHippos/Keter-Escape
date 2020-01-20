package com.onewisebit.scpescape.modesList.model

import com.onewisebit.scpescape.model.ModeDataClass
import com.onewisebit.scpescape.model.repositories.InModelNewRepository
import com.onewisebit.scpescape.modesList.GameModesContract

class GameModesModelImpl(modeRepository: InModelNewRepository) : GameModesContract.GameModesModel {

    private var modeRepo: InModelNewRepository = modeRepository

    override suspend fun getModes(): List<ModeDataClass> {
        // TODO: throw an error if list is empty
        return modeRepo.getAllModes() ?: emptyList()
    }
}