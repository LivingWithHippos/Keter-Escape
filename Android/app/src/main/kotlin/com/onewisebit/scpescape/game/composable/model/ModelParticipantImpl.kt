package com.onewisebit.scpescape.game.composable.model

import com.onewisebit.scpescape.game.composable.ContractParticipant
import com.onewisebit.scpescape.model.entities.Participant
import com.onewisebit.scpescape.model.entities.Round
import com.onewisebit.scpescape.model.entities.State
import com.onewisebit.scpescape.model.entities.Turn
import com.onewisebit.scpescape.model.repositories.InParticipantRepository
import com.onewisebit.scpescape.model.repositories.InStateRepository

open class ModelParticipantImpl(
    val participantRepository: InParticipantRepository,
    val stateRepository: InStateRepository
) : ContractParticipant.ModelParticipant {

    override suspend fun getParticipants(gameID: Long): List<Participant> =
        participantRepository.getGameParticipantsBlocking(gameID)

    override suspend fun getParticipant(gameID: Long, playerId: Long): Participant =
        participantRepository.getParticipant(gameID, playerId)

    override suspend fun getCurrentParticipant(gameID: Long): Participant =
        participantRepository.getCurrentParticipant(gameID)

    override suspend fun getMissingRoundParticipants(gameID: Long): List<Participant> =
        participantRepository.getMissingParticipants(gameID)

    override suspend fun setGameParticipantRole(gameID: Long, playerID: Long, roleName: String) =
        participantRepository.setGameParticipantRole(gameID, playerID, roleName)

    override suspend fun setParticipantState(gameID: Long, playerID: Long, state: Int) =
        participantRepository.setParticipantState(gameID, playerID, state)

    override suspend fun setParticipantStateRound(round: Round, playerID: Long, stateName: String) {
        stateRepository.insertState(State(
            0,
            stateName,
            round.gameID,
            round.num,
            null,
            playerID,
            true
        ))
    }

    override suspend fun setParticipantStateTurn(turn: Turn, playerID: Long, stateName: String) {
        stateRepository.insertState(State(
            0,
            stateName,
            turn.gameID,
            turn.roundNumber,
            turn.turnNumber,
            playerID,
            true
        ))
    }

    override suspend fun getGroup(gameID: Long, playerId: Long): String =
        participantRepository.getParticipantGroup(gameID, playerId)

    override suspend fun getParticipantStates(gameID: Long, playerID: Long): List<State> = stateRepository.getParticipantStates(gameID, playerID)

    override suspend fun getAllParticipantsActiveStates(gameID: Long): List<State>  = stateRepository.getAllParticipantsActiveStates(gameID)

    override suspend fun getAllParticipantsStates(gameID: Long): List<State> = stateRepository.getAllParticipantsStates(gameID)

    override suspend fun getParticipantsDeadStates(gameID: Long): List<State> = stateRepository.getParticipantsDeadStates(gameID)

    override suspend fun getRoundStates(gameID: Long, roundNumber: Int): List<State> = stateRepository.getRoundStates(gameID, roundNumber)

    override suspend fun getActiveRoundStates(gameID: Long, roundNumber: Int): List<State> = stateRepository.getActiveRoundStates(gameID, roundNumber)

    override suspend fun insertState(state: State): Long = stateRepository.insertState(state)

    override suspend fun setStateActive(stateID: Long, active: Boolean) = stateRepository.setStateActive(stateID, active)

    override suspend fun removeState(state: State) = stateRepository.removeState(state)

}