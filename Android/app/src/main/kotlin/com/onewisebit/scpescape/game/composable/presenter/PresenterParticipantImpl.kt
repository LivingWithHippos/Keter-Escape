package com.onewisebit.scpescape.game.composable.presenter

import com.onewisebit.scpescape.game.composable.ContractParticipant
import com.onewisebit.scpescape.model.entities.Participant
import com.onewisebit.scpescape.model.entities.Round
import com.onewisebit.scpescape.model.entities.State
import com.onewisebit.scpescape.utilities.PARTICIPANT_STATE_ALIVE
import com.onewisebit.scpescape.utilities.PARTICIPANT_STATE_DEAD
import com.onewisebit.scpescape.utilities.STATE_DEAD

open class PresenterParticipantImpl(
    val modelParticipant: ContractParticipant.ModelParticipant,
    val gameID: Long
) : ContractParticipant.PresenterParticipant {


    override suspend fun getParticipants(): List<Participant>? =
        modelParticipant.getParticipants(gameID)

    override suspend fun getAliveParticipants(): List<Participant> {
        return getParticipants()?.filter { it.stateValue == PARTICIPANT_STATE_ALIVE }
            ?: throw IllegalArgumentException("No participants found for game $gameID")
    }

    override suspend fun getCurrentParticipant(): Participant =
        modelParticipant.getCurrentParticipant(gameID)

    override suspend fun getParticipant(playerId: Long): Participant =
        modelParticipant.getParticipant(gameID, playerId)

    override suspend fun killParticipantsList(ids: List<Long>, currentRound: Round?) {
        //todo: use only the new State entity
        ids.forEach {
            modelParticipant.setParticipantState(gameID, it, PARTICIPANT_STATE_DEAD)
        }
        currentRound?.let { round ->
            ids.forEach { player ->
                modelParticipant.setParticipantStateRound(round, player, STATE_DEAD)
            }
        }
    }

    override suspend fun getGroup(playerId: Long): String =
        modelParticipant.getGroup(gameID, playerId)

    override suspend fun getParticipantStates(gameID: Long, playerID: Long): List<State> =
        modelParticipant.getParticipantStates(gameID, playerID)

    override suspend fun getAllParticipantsActiveStates(gameID: Long): List<State> =
        modelParticipant.getAllParticipantsActiveStates(gameID)

    override suspend fun getAllParticipantsStates(gameID: Long): List<State> =
        modelParticipant.getAllParticipantsStates(gameID)

    override suspend fun getParticipantsDeadStates(gameID: Long): List<State> =
        modelParticipant.getParticipantsDeadStates(gameID)

    override suspend fun getRoundStates(gameID: Long, roundNumber: Int): List<State> =
        modelParticipant.getRoundStates(gameID, roundNumber)

    override suspend fun getActiveRoundStates(gameID: Long, roundNumber: Int): List<State> =
        modelParticipant.getActiveRoundStates(gameID, roundNumber)

    override suspend fun insertState(state: State): Long = modelParticipant.insertState(state)

    override suspend fun setStateActive(stateID: Long, active: Boolean) =
        modelParticipant.setStateActive(stateID, active)

    override suspend fun removeState(state: State) = modelParticipant.removeState(state)
}