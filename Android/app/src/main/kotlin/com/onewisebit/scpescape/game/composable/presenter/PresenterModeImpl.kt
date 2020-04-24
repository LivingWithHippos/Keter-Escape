package com.onewisebit.scpescape.game.composable.presenter

import com.onewisebit.scpescape.game.composable.ContractMode
import com.onewisebit.scpescape.model.parsed.ModeDataClass
import com.onewisebit.scpescape.model.parsed.VictoryCondition


class PresenterModeImpl(val modelMode: ContractMode.ModelMode, val gameID: Long) :
    ContractMode.PresenterMode {
    override suspend fun getMode(): ModeDataClass {
        return modelMode.getMode(gameID)
            ?: throw IllegalArgumentException("Couldn't load mode from game $gameID")
    }

    override suspend fun getVictoryConditions(): List<VictoryCondition> =
        modelMode.getVictoryConditions(gameID)
}