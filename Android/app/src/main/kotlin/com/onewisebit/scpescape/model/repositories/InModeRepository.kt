package com.onewisebit.scpescape.model.repositories

import com.onewisebit.scpescape.model.entities.Mode
import io.reactivex.Single

interface InModeRepository {

    fun getMode(id: Int): Single<Mode>
    fun getMinPlayers(id: Int): Int
    fun getMaxPlayers(id: Int): Int
    fun insertMode(mode: Mode)
    fun getAllModes(): List<Mode>
    suspend fun insertAll(modes: List<Mode>)
}