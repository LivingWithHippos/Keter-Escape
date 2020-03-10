package com.onewisebit.scpescape.game.intro

import com.onewisebit.scpescape.game.composable.ContractMode
import com.onewisebit.scpescape.game.composable.ContractParticipant

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