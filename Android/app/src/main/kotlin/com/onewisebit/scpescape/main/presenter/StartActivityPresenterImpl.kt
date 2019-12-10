package com.onewisebit.scpescape.main.presenter

import com.onewisebit.scpescape.main.StartContract
import kotlin.random.Random

class StartActivityPresenterImpl(
    sView: StartContract.StartView,
    sModel: StartContract.StartModel
) :
    StartContract.StartPresenter {

    private var view: StartContract.StartView = sView
    private var model: StartContract.StartModel = sModel

    override fun setView(view: StartContract.StartView) {
        this.view = view
    }

    override fun addPlayer(name: String) {
        if (name.isEmpty() or name.isBlank())
            model.createNewPlayer(generateDClassName())
        else
            model.createNewPlayer(name)
    }

    //TODO: check if it's better to declare this in the interface
    private fun generateDClassName(): String {
        //TODO: check model for name collision
        //maybe this name is not good since it can be confused with the role
        val dNumber = Random.nextInt(10000) + 1000
        return "D-$dNumber"
    }

}