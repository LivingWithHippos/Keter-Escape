package com.onewisebit.scpescape.game

import com.onewisebit.scpescape.model.entities.Game
import io.reactivex.Single

interface IntroContract {

    interface IntroModel{
        fun getGame(id: Long): Single<Game>
    }

    interface IntroView{
        fun setupGame(game:Single<Game>)
    }

    interface IntroPresenter{
        fun setup(id:Long)
    }
}