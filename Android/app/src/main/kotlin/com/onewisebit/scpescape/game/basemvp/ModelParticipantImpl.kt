package com.onewisebit.scpescape.game.basemvp

import com.onewisebit.scpescape.model.entities.Participant
import com.onewisebit.scpescape.model.repositories.InParticipantRepository

open class ModelParticipantImpl(
    val participantRepository: InParticipantRepository
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
}