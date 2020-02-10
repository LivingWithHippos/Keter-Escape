package com.onewisebit.scpescape.game.presenter

import com.onewisebit.scpescape.game.PlayerTurnContract
import com.onewisebit.scpescape.game.basemvp.ContractParticipant
import com.onewisebit.scpescape.game.basemvp.ContractPlayer

class PlayerTurnPresenterImpl(
    val view: PlayerTurnContract.PlayerTurnView,
    model: PlayerTurnContract.PlayerTurnModel,
    private val participantPresenter: ContractParticipant.PresenterParticipant,
    private val playerPresenter: ContractPlayer.PresenterPlayer,
    val gameID: Long
) : PlayerTurnContract.PlayerTurnPresenter,
    ContractPlayer.PresenterPlayer by playerPresenter,
    ContractParticipant.PresenterParticipant by participantPresenter {

    override suspend fun loadValues() {
        val participant = getCurrentParticipant()
        val player = getPlayer(participant.playerID)
        view.initView(player.name, "playerRoleName", "playerRoleDescription")
    }
}