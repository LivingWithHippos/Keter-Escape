package com.onewisebit.scpescape.modesList

import com.onewisebit.scpescape.model.parsed.ModeDataClass

interface GameModesContract {

    interface GameModesView {
        fun setList(modes: List<ModeDataClass>)
    }

    interface GameModesPresenter {
        fun initView()
        suspend fun getModes(): List<ModeDataClass>
    }

    interface GameModesModel {
        suspend fun getModes(): List<ModeDataClass>
    }
}