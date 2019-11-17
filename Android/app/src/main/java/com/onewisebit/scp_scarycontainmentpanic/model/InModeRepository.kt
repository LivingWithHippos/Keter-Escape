package com.onewisebit.scp_scarycontainmentpanic.model

interface InModeRepository {

    fun getMode(id: Long): Mode
    fun getMinPlayers(id: Long): Int
    fun getMaxPlayers(id: Long): Int
    fun insertMode(mode: Mode)
    fun getAllModes(): List<Mode>
    suspend fun insertAll(modes: List<Mode>)
}