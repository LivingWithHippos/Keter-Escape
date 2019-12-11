package com.onewisebit.scpescape.game

import com.onewisebit.scpescape.model.entities.Game

interface IntroContract {

    interface IntroModel{
        abstract fun getGame(id: Long): Game
    }

    interface IntroView{
        fun setupGame(game:Game)
    }

    interface IntroPresenter{
        fun setup(id:Long)
    }
}