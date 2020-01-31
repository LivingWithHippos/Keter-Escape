package com.onewisebit.scpescape.game.model

import com.onewisebit.scpescape.game.IntroContract
import com.onewisebit.scpescape.game.basemvp.ContractMode
import com.onewisebit.scpescape.game.basemvp.ContractParticipant
import com.onewisebit.scpescape.model.repositories.*

class IntroModelImpl(
    val participantRepository: InParticipantRepository,
    val participantModel: ContractParticipant.ModelParticipant,
    val modeModel: ContractMode.ModelMode):
    ContractParticipant.ModelParticipant by participantModel,
    ContractMode.ModelMode by modeModel,
    IntroContract.IntroModel {

    override suspend fun assignRole(gameID: Long, playerID: Long, roleName: String) =
        participantRepository.setGameParticipantRole(gameID, playerID, roleName)
}