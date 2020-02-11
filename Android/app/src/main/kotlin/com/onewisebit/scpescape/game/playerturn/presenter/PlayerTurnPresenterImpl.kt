package com.onewisebit.scpescape.game.playerturn.presenter

import com.onewisebit.scpescape.game.playerturn.PlayerTurnContract
import com.onewisebit.scpescape.game.composable.ContractParticipant
import com.onewisebit.scpescape.game.composable.ContractPlayer

class PlayerTurnPresenterImpl(
    val view: PlayerTurnContract.PlayerTurnView,
    model: PlayerTurnContract.PlayerTurnModel,
    private val participantPresenter: ContractParticipant.PresenterParticipant,
    private val playerPresenter: ContractPlayer.PresenterPlayer,
    val gameID: Long
) : PlayerTurnContract.PlayerTurnPresenter,
    ContractPlayer.PresenterPlayer by playerPresenter,
    ContractParticipant.PresenterParticipant by participantPresenter {
}