package com.onewisebit.scpescape.game.presenter

import com.onewisebit.scpescape.game.RoundInfoContract
import com.onewisebit.scpescape.game.basemvp.ContractRound

class RoundInfoPresenterImpl(
    roundiInfoView: RoundInfoContract.RoundInfoView,
    roundInfoModel: RoundInfoContract.RoundInfoModel,
    roundPresenter: ContractRound.PresenterRound,
    val gameID: Long
) : RoundInfoContract.RoundInfoPresenter, ContractRound.PresenterRound by roundPresenter {

}