package com.onewisebit.scpescape.model.repositories

import com.onewisebit.scpescape.model.parsed.InfoTurn
import com.onewisebit.scpescape.model.parsed.TurnAction
import com.onewisebit.scpescape.model.parsed.VoteTurn

interface InTurnActionRepository {

    suspend fun getTemplates(): List<TurnAction>

    suspend fun getRoleAction(modeId: Int, roleName: String, roundCode: String): TurnAction

    suspend fun getModeActions(modeId: Int): List<TurnAction>
    //TODO: check if these two can be private in the repo
    suspend fun getVote(path: String): List<VoteTurn>?

    suspend fun getInfo(path: String): List<InfoTurn>?
}