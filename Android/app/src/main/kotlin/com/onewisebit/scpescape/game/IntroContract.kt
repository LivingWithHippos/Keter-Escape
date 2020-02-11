package com.onewisebit.scpescape.game

import com.onewisebit.scpescape.game.composable.ContractMode
import com.onewisebit.scpescape.game.composable.ContractParticipant

//TODO: for simple fragment a contract may not be needed and we could just pass values from the activity
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