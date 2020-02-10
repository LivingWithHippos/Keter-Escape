package com.onewisebit.scpescape.game

import com.onewisebit.scpescape.game.basemvp.ContractParticipant
import com.onewisebit.scpescape.game.basemvp.ContractPlayer

interface PlayerTurnContract {

    interface PlayerTurnView {
        fun initView(playerName: String, playerRoleName: String, playerRoleDescription: String)
    }

    interface PlayerTurnModel : ContractPlayer.ModelPlayer, ContractParticipant.ModelParticipant

    interface PlayerTurnPresenter : ContractPlayer.PresenterPlayer,
        ContractParticipant.PresenterParticipant {
        suspend fun loadValues()
    }
}