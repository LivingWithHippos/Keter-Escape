package com.onewisebit.scpescape.game.playerturn

import com.onewisebit.scpescape.game.composable.ContractParticipant
import com.onewisebit.scpescape.game.composable.ContractPlayer

interface PlayerTurnContract {

    interface PlayerTurnView {
        fun initView(playerName: String?, playerRoleName: String?, playerRoleDescription: String?)
    }

    interface PlayerTurnModel : ContractPlayer.ModelPlayer, ContractParticipant.ModelParticipant

    interface PlayerTurnPresenter : ContractPlayer.PresenterPlayer,
        ContractParticipant.PresenterParticipant {
        suspend fun loadValues()
    }
}