package com.onewisebit.scpescape.game.basemvp

import com.onewisebit.scpescape.model.parsed.ModeDataClass


class PresenterModeImpl(val modelMode: ContractMode.ModelMode, val gameID: Long) :
    ContractMode.PresenterMode {
    override suspend fun getMode(): ModeDataClass {
        return modelMode.getMode(gameID)
            ?: throw IllegalArgumentException("Couldn't load mode from game $gameID")
    }
}