package com.onewisebit.scpescape.game.view

import com.onewisebit.scpescape.BaseSCPActivity
import com.onewisebit.scpescape.game.GameContract
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class GameActivity: BaseSCPActivity(), GameContract.GameView{

    private val presenter: GameContract.GamePresenter by inject { parametersOf(this) }
}