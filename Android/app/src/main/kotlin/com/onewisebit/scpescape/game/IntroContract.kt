package com.onewisebit.scpescape.game

import com.onewisebit.scpescape.model.ModeDataClass
import com.onewisebit.scpescape.model.entities.Game

interface IntroContract {

    interface IntroModel {
        suspend fun getGame(id: Long): Game
        suspend fun getMode(id: Long): ModeDataClass?
    }

    interface IntroView {
    }

    interface IntroPresenter {
        suspend fun getGame(id: Long): Game
        suspend fun getMode(id: Long): ModeDataClass?
    }
}