package com.onewisebit.scp_scarycontainmentpanic

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
