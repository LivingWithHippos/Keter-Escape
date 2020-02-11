package com.onewisebit.scpescape.game.roundinfo

import com.onewisebit.scpescape.game.composable.ContractRound
import com.onewisebit.scpescape.model.parsed.RoundDetails

//TODO: for simple fragment a contract may not be needed and we could just pass values from the activity
interface RoundInfoContract {

    interface RoundInfoModel : ContractRound.ModelRound

    interface RoundInfoView {
        fun initView(info: RoundDetails)
    }

    interface RoundInfoPresenter : ContractRound.PresenterRound {
        suspend fun loadRoundInfo()
    }
}