package com.onewisebit.scp_scarycontainmentpanic.model

import android.content.SharedPreferences
import com.onewisebit.scp_scarycontainmentpanic.StartContract
import com.onewisebit.scp_scarycontainmentpanic.utilities.CURRENT_THEME
import com.onewisebit.scp_scarycontainmentpanic.utilities.DEFAULT_THEME


class StartActivityModel(playerRepository: PlayerRepository, sharedPreferences: SharedPreferences) :
    StartContract.StartModel {

    private var repository: PlayerRepository = playerRepository
    private var preferences: SharedPreferences = sharedPreferences

    override fun getTheme(): String {
        val theme = preferences.getString(CURRENT_THEME, DEFAULT_THEME)
        return theme ?: DEFAULT_THEME
    }

    override fun createNewPlayer(name: String) {
        //TODO: add image
        repository.insertPlayer(Player(0, name, "none"))
    }
}