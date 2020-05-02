package com.onewisebit.scpescape.model.repositories

import com.onewisebit.scpescape.model.entities.State

interface InStateRepository {
    suspend fun getParticipantStates(gameID: Long, playerID: Long): List<State>
    suspend fun getParticipantActiveStates(gameID: Long, playerID: Long): List<State>
    suspend fun getAllGameActiveStates(gameID: Long): List<State>
    suspend fun getAllGameStates(gameID: Long): List<State>
    suspend fun getGameDeadStates(gameID: Long): List<State>
    suspend fun getRoundStates(gameID: Long, roundNumber: Int): List<State>
    suspend fun getActiveRoundStates(gameID: Long, roundNumber: Int): List<State>
    suspend fun insertState(state: State): Long
    suspend fun setStateActive(stateID: Long, active: Boolean)
    suspend fun removeParticipant(state: State)
}