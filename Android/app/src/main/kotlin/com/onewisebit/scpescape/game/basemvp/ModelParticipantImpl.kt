package com.onewisebit.scpescape.game.basemvp

import com.onewisebit.scpescape.model.entities.Participant
import com.onewisebit.scpescape.model.repositories.*

class ModelParticipantImpl(
    val participantRepository: InParticipantRepository,
    val roundRepository: InRoundRepository,
    val turnRepository: InTurnRepository
): ContractParticipant.ModelParticipant {

    override suspend fun getParticipants(gameID: Long): List<Participant> = participantRepository.getGameParticipantsBlocking(gameID)

    override suspend fun getCurrentParticipant(gameID: Long): Participant = turnRepository.getCurrentParticipant(gameID)

    override suspend fun getMissingRoundParticipants(gameID: Long): List<Participant> = roundRepository.getMissingParticipants(gameID)
}