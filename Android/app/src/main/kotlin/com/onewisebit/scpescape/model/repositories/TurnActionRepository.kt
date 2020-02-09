package com.onewisebit.scpescape.model.repositories

import com.onewisebit.scpescape.model.parsed.TurnAction

class TurnActionRepository: InTurnActionRepository {

    override suspend fun getTemplates(): List<TurnAction> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getAction(mode: Int, name: String): TurnAction {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getAllActions(mode: Int): List<TurnAction> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getPlayerAction(playerId: Long): TurnAction {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}