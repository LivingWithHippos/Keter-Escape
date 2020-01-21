package com.onewisebit.scpescape.main.model

import android.content.SharedPreferences
import com.onewisebit.scpescape.main.StartContract
import com.onewisebit.scpescape.model.entities.Player
import com.onewisebit.scpescape.model.repositories.InPlayerRepository
import com.onewisebit.scpescape.model.repositories.PlayerRepository
import com.onewisebit.scpescape.utilities.CURRENT_THEME
import com.onewisebit.scpescape.utilities.DEFAULT_THEME


class StartActivityModel(playerRepository: InPlayerRepository, sharedPreferences: SharedPreferences) :
    StartContract.StartModel {

    private var repository: InPlayerRepository = playerRepository
    private var preferences: SharedPreferences = sharedPreferences

    override fun getTheme(): String {
        val theme = preferences.getString(CURRENT_THEME, DEFAULT_THEME)
        return theme ?: DEFAULT_THEME
    }

    override fun createNewPlayer(name: String) {
        //TODO: add image
        repository.insertPlayer(
            Player(
                0,
                name,
                "none"
            )
        )
    }
}