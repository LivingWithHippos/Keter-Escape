package com.onewisebit.scpescape.game.composable.presenter

import com.onewisebit.scpescape.game.composable.ContractParticipant
import com.onewisebit.scpescape.model.entities.Participant
import com.onewisebit.scpescape.utilities.PARTICIPANT_STATE_ALIVE
import com.onewisebit.scpescape.utilities.PARTICIPANT_STATE_DEAD

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

    override suspend fun killParticipantsList(ids: List<Long>) {
        ids.forEach {
            modelParticipant.setParticipantState(gameID, it, PARTICIPANT_STATE_DEAD)
        }
    }

    override suspend fun getGroup(playerId: Long): String =
        modelParticipant.getGroup(gameID, playerId)
}