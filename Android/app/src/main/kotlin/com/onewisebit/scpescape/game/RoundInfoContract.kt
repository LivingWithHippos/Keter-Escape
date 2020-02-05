package com.onewisebit.scpescape.game

import com.onewisebit.scpescape.game.basemvp.ContractRound
import com.onewisebit.scpescape.model.parsed.RoundDetails

interface RoundInfoContract {

    interface RoundInfoModel : ContractRound.ModelRound

    interface RoundInfoView {
        fun initView(info: RoundDetails?)
    }

    interface RoundInfoPresenter : ContractRound.PresenterRound {
        suspend fun loadRoundInfo()
    }
}