package com.onewisebit.scpescape.modesList.presenter

import com.onewisebit.scpescape.model.ModeDataClass
import com.onewisebit.scpescape.modesList.GameModesContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GameModesPresenterImpl(gView : GameModesContract.GameModesView, gModel: GameModesContract.GameModesModel) : GameModesContract.GameModesPresenter{

    private var view  = gView
    private var model   = gModel

    override fun initView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getModes(): List<ModeDataClass> = model.getModes()
}