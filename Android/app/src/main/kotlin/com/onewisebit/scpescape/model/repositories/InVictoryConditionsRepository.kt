package com.onewisebit.scpescape.model.repositories

import com.onewisebit.scpescape.model.parsed.VictoryCondition

interface InVictoryConditionsRepository {
    suspend fun getVictoryConditions(modeID: Int): List<VictoryCondition>
}