package com.onewisebit.scpescape.main.activity.presenter

import android.util.Log
import com.onewisebit.scpescape.main.activity.StartContract
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



    override fun loadGame(gameID: Long) {
        /** LOAD A GAME
         * get the Game entity
         * do some checks like finished etc maybe
         * load the game activity and initialize the game machine at the right moment
         * CASES:
         *  it's a player turn and he has finished his votes
         *  it's a player turn and he hasn't finished his votes
         *  it's a round check turn
         */
        // try get the last saved turn and:
        //
        Log.d("StartActivityPresenter", "Got to load game $gameID")
    }

    //TODO: check if it's better to declare this in the interface
    private fun generateDClassName(): String {
        //TODO: check model for name collision
        //maybe this name is not good since it can be confused with the role
        val dNumber = Random.nextInt(10000) + 1000
        return "D-$dNumber"
    }

}