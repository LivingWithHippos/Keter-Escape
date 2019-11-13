package com.onewisebit.scp_scarycontainmentpanic

import android.content.Context

interface StartContract {

    interface StartView {
        fun initView()
        fun updateTheme()
    }

    interface StartPresenter {
        fun onNewGameSelected()
        fun onSettingsSelected()
        fun onAboutSelected()
    }

    interface StartModel {
        fun init(context : Context)
        fun getTheme(): String
    }
}
