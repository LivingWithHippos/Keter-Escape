package com.onewisebit.scpescape.game.intro.model

import com.onewisebit.scpescape.game.intro.IntroContract
import com.onewisebit.scpescape.game.composable.ContractMode
import com.onewisebit.scpescape.game.composable.ContractParticipant

class IntroModelImpl(
    val participantModel: ContractParticipant.ModelParticipant,
    val modeModel: ContractMode.ModelMode
) :
    ContractParticipant.ModelParticipant by participantModel,
    ContractMode.ModelMode by modeModel,
    IntroContract.IntroModel {

    override suspend fun assignRole(gameID: Long, playerID: Long, roleName: String) =
        setGameParticipantRole(gameID, playerID, roleName)
}