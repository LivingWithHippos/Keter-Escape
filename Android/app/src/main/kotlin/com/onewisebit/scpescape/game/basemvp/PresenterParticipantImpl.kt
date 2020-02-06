package com.onewisebit.scpescape.game.basemvp

import com.onewisebit.scpescape.model.entities.Participant
import com.onewisebit.scpescape.utilities.PARTICIPANT_STATE_ALIVE

open class PresenterParticipantImpl(
    val modelParticipant: ContractParticipant.ModelParticipant,
    val gameID: Long
) : ContractParticipant.PresenterParticipant {


    override suspend fun getParticipants(): List<Participant>? =
        modelParticipant.getParticipants(gameID)

    override suspend fun getAliveParticipants(): List<Participant> {
        return getParticipants()?.filter { it.stateValue == PARTICIPANT_STATE_ALIVE } ?: throw IllegalArgumentException("No participants found for game $gameID")
    }

    override suspend fun getCurrentParticipant(): Participant =
        modelParticipant.getCurrentParticipant(gameID)
}