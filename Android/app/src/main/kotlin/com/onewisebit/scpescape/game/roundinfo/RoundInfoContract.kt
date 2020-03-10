package com.onewisebit.scpescape.game.roundinfo

import com.onewisebit.scpescape.game.composable.ContractRound
import com.onewisebit.scpescape.model.parsed.RoundDetails

interface RoundInfoContract {

    interface RoundInfoModel : ContractRound.ModelRound

    interface RoundInfoView {
        fun initView(info: RoundDetails)
    }

    interface RoundInfoPresenter : ContractRound.PresenterRound {
        suspend fun loadRoundInfo()
    }
}