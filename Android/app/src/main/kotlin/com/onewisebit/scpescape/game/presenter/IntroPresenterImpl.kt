package com.onewisebit.scpescape.game.presenter

import com.onewisebit.scpescape.game.IntroContract
import com.onewisebit.scpescape.model.entities.Game
import com.onewisebit.scpescape.model.entities.Mode
import io.reactivex.Single

class IntroPresenterImpl(
    iView: IntroContract.IntroView,
    iModel: IntroContract.IntroModel
) : IntroContract.IntroPresenter {

    private var view: IntroContract.IntroView = iView
    private var model: IntroContract.IntroModel = iModel

    override fun setup(id: Long) {
        val game: Single<Game> = model.getGame(id)
        val mode: Single<Mode> = model.getMode(id)
        view.setupGame(game, mode)
    }

}