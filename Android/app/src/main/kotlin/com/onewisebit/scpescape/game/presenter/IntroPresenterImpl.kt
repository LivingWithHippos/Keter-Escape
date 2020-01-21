package com.onewisebit.scpescape.game.presenter

import com.onewisebit.scpescape.game.IntroContract
import com.onewisebit.scpescape.model.ModeDataClass
import com.onewisebit.scpescape.model.entities.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IntroPresenterImpl(
    iView: IntroContract.IntroView,
    iModel: IntroContract.IntroModel
) : IntroContract.IntroPresenter {

    private var view: IntroContract.IntroView = iView
    private var model: IntroContract.IntroModel = iModel

    override suspend fun getGame(id: Long): Game = model.getGame(id)

    override suspend fun getMode(id: Long): ModeDataClass? = model.getMode(id)

}