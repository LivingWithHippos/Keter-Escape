package com.onewisebit.scpescape.game.roundinfo.presenter

import com.onewisebit.scpescape.game.roundinfo.RoundInfoContract
import com.onewisebit.scpescape.game.composable.ContractRound

class RoundInfoPresenterImpl(
    val roundiInfoView: RoundInfoContract.RoundInfoView,
    val roundInfoModel: RoundInfoContract.RoundInfoModel,
    val roundPresenter: ContractRound.PresenterRound,
    val gameID: Long
) : RoundInfoContract.RoundInfoPresenter, ContractRound.PresenterRound by roundPresenter {

    override suspend fun loadRoundInfo() {
        val info = roundPresenter.getCurrentRoundDetails()
        if (info != null) {
            roundiInfoView.initView(info)
        } else {
            throw IllegalArgumentException("Loaded round info were null. Game $gameID.")
        }
    }

}