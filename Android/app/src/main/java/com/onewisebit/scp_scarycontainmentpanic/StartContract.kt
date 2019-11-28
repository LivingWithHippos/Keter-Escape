package com.onewisebit.scp_scarycontainmentpanic

interface StartContract {

    interface StartView {
        fun updateTheme()
    }

    interface StartPresenter {
        fun setView(view: StartView)
    }

    interface StartModel {
        fun getTheme(): String
    }
}
