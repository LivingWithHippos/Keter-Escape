package com.onewisebit.scp_scarycontainmentpanic.presenters

import android.content.Context
import com.onewisebit.scp_scarycontainmentpanic.StartContract
import com.onewisebit.scp_scarycontainmentpanic.model.StartActivityModel

class StartActivityPresenter(view: StartContract.StartView, context : Context): StartContract.StartPresenter {

    private var view : StartContract.StartView = view
    private var model : StartContract.StartModel = StartActivityModel()

    init {
        view.initView()
        model.init(context)
    }

    override fun onNewGameSelected() {
        //TODO: go to new game fragment
    }

    override fun onSettingsSelected() {
        //TODO: open settings
    }

    override fun onAboutSelected() {
        //TODO: open about page
    }
}