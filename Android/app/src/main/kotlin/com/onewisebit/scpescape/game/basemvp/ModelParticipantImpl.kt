package com.onewisebit.scpescape.game.basemvp

import com.onewisebit.scpescape.model.entities.Participant
import com.onewisebit.scpescape.model.repositories.InParticipantRepository

open class ModelParticipantImpl(
    val participantRepository: InParticipantRepository
) : ContractParticipant.ModelParticipant {

    override suspend fun getParticipants(gameID: Long): List<Participant> =
        participantRepository.getGameParticipantsBlocking(gameID)

    override suspend fun getCurrentParticipant(gameID: Long): Participant =
        participantRepository.getCurrentParticipant(gameID)

    override suspend fun getMissingRoundParticipants(gameID: Long): List<Participant> =
        participantRepository.getMissingParticipants(gameID)
}