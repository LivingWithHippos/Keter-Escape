package com.onewisebit.scp_scarycontainmentpanic.presenters

import com.onewisebit.scp_scarycontainmentpanic.PlayersContract

class PlayersPresenterImpl (pView:PlayersContract.PlayersView,
                            pModel:PlayersContract.PlayersModel) : PlayersContract.PlayersPresenter {

    private var view: PlayersContract.PlayersView = pView
    private var model: PlayersContract.PlayersModel = pModel

    override fun setPlayers() {
        view.initView(model.getAllPlayers())
    }
}