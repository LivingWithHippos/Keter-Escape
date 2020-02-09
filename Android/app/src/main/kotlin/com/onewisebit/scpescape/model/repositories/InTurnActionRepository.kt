package com.onewisebit.scpescape.model.repositories

import com.onewisebit.scpescape.model.parsed.TurnAction

interface InTurnActionRepository {

    suspend fun getTemplates(): List<TurnAction>

    suspend fun getAction(mode: Int, name: String): TurnAction

    suspend fun getPlayerAction(playerId: Long): TurnAction

    suspend fun getAllActions(mode: Int): List<TurnAction>
}