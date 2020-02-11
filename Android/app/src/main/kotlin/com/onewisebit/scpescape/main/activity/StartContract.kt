package com.onewisebit.scpescape.main.activity

interface StartContract {

    interface StartView {
        fun updateTheme()
    }

    interface StartPresenter {
        fun setView(view: StartView)
        fun addPlayer(name: String)
    }

    interface StartModel {
        fun getTheme(): String
        fun createNewPlayer(name: String)
    }
}
