package com.onewisebit.scpescape.game

import com.onewisebit.scpescape.game.basemvp.ContractMode
import com.onewisebit.scpescape.game.basemvp.ContractParticipant

interface IntroContract {

    interface IntroModel : ContractParticipant.ModelParticipant, ContractMode.ModelMode {
        suspend fun assignRole(gameID: Long, playerID: Long, roleName: String)
    }

    interface IntroView

    interface IntroPresenter : ContractParticipant.PresenterParticipant,
        ContractMode.PresenterMode {
        suspend fun assignRoles()
    }
}