package com.onewisebit.scpescape.game.roundinfo.presenter

import com.onewisebit.scpescape.game.composable.ContractMode
import com.onewisebit.scpescape.game.composable.ContractRound
import com.onewisebit.scpescape.game.roundinfo.RoundInfoContract

class RoundInfoPresenterImpl(
    val roundiInfoView: RoundInfoContract.RoundInfoView,
    val roundInfoModel: RoundInfoContract.RoundInfoModel,
    val roundPresenter: ContractRound.PresenterRound,
    val modePresenter: ContractMode.PresenterMode,
    val gameID: Long
) : RoundInfoContract.RoundInfoPresenter, ContractRound.PresenterRound by roundPresenter, ContractMode.PresenterMode by modePresenter {

    override suspend fun loadRoundInfo() {
        val info = roundPresenter.getCurrentRoundDetails()
        roundiInfoView.initView(info)
    }

    suspend fun getRoundType(): String {
        val round = roundPresenter.getCurrentRound()
        if (round == null)
            throw IllegalStateException("Obtained a null round in getRoundType.")
        else
            return round.details
    }

}