package com.onewisebit.scpescape.model.repositories

import com.onewisebit.scpescape.model.daos.StateDAO
import com.onewisebit.scpescape.model.entities.State

class StateRepository(private val stateDAO: StateDAO) : InStateRepository {
    override suspend fun getParticipantStates(gameID: Long, playerID: Long): List<State> {
        val states = stateDAO.getParticipantStates(gameID, playerID)
        return states ?: emptyList()
    }

    override suspend fun getParticipantActiveStates(gameID: Long, playerID: Long): List<State> {
        val states = stateDAO.getParticipantActiveStates(gameID, playerID)
        return states ?: emptyList()
    }

    override suspend fun getAllParticipantsActiveStates(gameID: Long): List<State> {
        val states = stateDAO.getAllGameActiveStates(gameID)
        return states ?: emptyList()
    }

    override suspend fun getAllParticipantsStates(gameID: Long): List<State> {
        val states = stateDAO.getAllGameStates(gameID)
        return states ?: emptyList()
    }

    override suspend fun getParticipantsDeadStates(gameID: Long): List<State> {
        val states = stateDAO.getGameDeadStates(gameID)
        return states ?: emptyList()
    }

    override suspend fun getRoundStates(gameID: Long, roundNumber: Int): List<State> {
        val states = stateDAO.getRoundStates(gameID, roundNumber)
        return states ?: emptyList()
    }

    override suspend fun getActiveRoundStates(gameID: Long, roundNumber: Int): List<State> {
        val states = stateDAO.getActiveRoundStates(gameID, roundNumber)
        return states ?: emptyList()
    }

    override suspend fun insertState(state: State): Long = stateDAO.insertState(state)

    override suspend fun setStateActive(stateID: Long, active: Boolean) =
        stateDAO.setStateActive(stateID, active)

    override suspend fun removeState(state: State) = stateDAO.removeState(state)

}