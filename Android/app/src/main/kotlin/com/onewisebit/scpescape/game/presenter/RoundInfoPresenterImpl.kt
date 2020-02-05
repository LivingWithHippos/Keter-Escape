package com.onewisebit.scpescape.game.presenter

import com.onewisebit.scpescape.game.RoundInfoContract
import com.onewisebit.scpescape.game.basemvp.ContractRound

class RoundInfoPresenterImpl(
    val roundiInfoView: RoundInfoContract.RoundInfoView,
    val roundInfoModel: RoundInfoContract.RoundInfoModel,
    val roundPresenter: ContractRound.PresenterRound,
    val gameID: Long
) : RoundInfoContract.RoundInfoPresenter, ContractRound.PresenterRound by roundPresenter {

    override suspend fun loadRoundInfo() {
        val info = roundPresenter.getCurrentRoundDetails()
        roundiInfoView.initView(info)
    }

}