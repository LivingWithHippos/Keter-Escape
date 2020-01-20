package com.onewisebit.scpescape.modesList.model

import com.onewisebit.scpescape.model.ModeDataClass
import com.onewisebit.scpescape.model.repositories.ModeNewRepository
import com.onewisebit.scpescape.model.repositories.ModeRepository
import com.onewisebit.scpescape.modesList.GameModesContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GameModesModelImpl(modeRepository: ModeNewRepository): GameModesContract.GameModesModel {

    private var modeRepo: ModeNewRepository = modeRepository

    override suspend fun getModes(): List<ModeDataClass> {
        // TODO: throw an error if list is empty
        return modeRepo.getAllModes() ?: emptyList()
    }
}