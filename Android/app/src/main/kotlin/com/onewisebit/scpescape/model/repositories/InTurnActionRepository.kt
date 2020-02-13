package com.onewisebit.scpescape.model.repositories

import com.onewisebit.scpescape.model.parsed.InfoTurn
import com.onewisebit.scpescape.model.parsed.RoleDetails
import com.onewisebit.scpescape.model.parsed.TurnAction
import com.onewisebit.scpescape.model.parsed.VoteSettings

interface InTurnActionRepository {

    suspend fun getTemplates(): List<TurnAction>

    suspend fun getPartialAction(modeId: Int, roleName: String, roundCode: String): TurnAction

    suspend fun getModeActions(modeId: Int): List<TurnAction>
    //TODO: check if these two can be private in the repo
    suspend fun getVote(path: String): List<VoteSettings>?

    suspend fun getInfo(path: String): List<InfoTurn>?

    suspend fun getMode(gameId: Long): Int

    suspend fun getRoleDetails(modeId: Int): List<RoleDetails>

    suspend fun getCompleteAction(modeId: Int, roleName: String, roundCode: String): TurnAction
}