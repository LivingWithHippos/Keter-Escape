package com.onewisebit.scp_scarycontainmentpanic.model

import android.content.Context
import android.content.SharedPreferences
import com.onewisebit.scp_scarycontainmentpanic.StartContract
import com.onewisebit.scp_scarycontainmentpanic.utilities.CURRENT_THEME
import com.onewisebit.scp_scarycontainmentpanic.utilities.DEFAULT_THEME
import com.onewisebit.scp_scarycontainmentpanic.utilities.PREF_FILE


class StartActivityModel : StartContract.StartModel {


    private var sharedPreferences: SharedPreferences? = null

    override fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
    }

    override fun getTheme(): String {
        val theme = sharedPreferences?.getString(CURRENT_THEME, DEFAULT_THEME)
        return theme ?: DEFAULT_THEME
    }
}