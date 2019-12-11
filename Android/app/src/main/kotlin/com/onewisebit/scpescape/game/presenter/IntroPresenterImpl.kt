package com.onewisebit.scpescape.game.presenter

import com.onewisebit.scpescape.game.IntroContract
import com.onewisebit.scpescape.model.entities.Game

class IntroPresenterImpl(
    iView: IntroContract.IntroView,
    iModel: IntroContract.IntroModel
) : IntroContract.IntroPresenter {

    private var view: IntroContract.IntroView = iView
    private var model: IntroContract.IntroModel = iModel

    override fun setup(id: Long) {
        val game: Game = model.getGame(id)
        view.setupGame(game)
    }

}