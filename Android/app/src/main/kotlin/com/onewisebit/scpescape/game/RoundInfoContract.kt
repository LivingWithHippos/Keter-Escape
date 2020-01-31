package com.onewisebit.scpescape.game

import com.onewisebit.scpescape.game.basemvp.ContractRound

interface RoundInfoContract {

    interface RoundInfoModel : ContractRound.ModelRound

    interface RoundInfoView

    interface RoundInfoPresenter : ContractRound.PresenterRound
}