package com.onewisebit.scpescape.modesList

import com.onewisebit.scpescape.model.ModeDataClass

interface GameModesContract {

    interface GameModesView {
        fun setList(modes: List<ModeDataClass>)
    }
    interface GameModesPresenter {
        fun initView()
    }
    interface GameModesModel {
        suspend fun getModes(): List<ModeDataClass>
    }
}