package com.onewisebit.scpescape.game

import com.onewisebit.scpescape.model.entities.Game
import com.onewisebit.scpescape.model.entities.Mode
import io.reactivex.Single

interface IntroContract {

    interface IntroModel{
        fun getGame(id: Long): Single<Game>
        fun getMode(id: Long): Single<Mode>
    }

    interface IntroView{
        fun setupGame(game:Single<Game>, mode: Single<Mode>)
    }

    interface IntroPresenter{
        fun setup(id:Long)
    }
}