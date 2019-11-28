package com.onewisebit.scp_scarycontainmentpanic.presenters

import com.onewisebit.scp_scarycontainmentpanic.StartContract

class StartActivityPresenter(sView: StartContract.StartView,
                             sModel: StartContract.StartModel):
    StartContract.StartPresenter {

    private var view: StartContract.StartView = sView
    private var model: StartContract.StartModel = sModel

    override fun setView(view: StartContract.StartView) {
        this.view = view
    }

    override fun addPlayer(name: String) {
        model.createNewPlayer(name)
    }
}