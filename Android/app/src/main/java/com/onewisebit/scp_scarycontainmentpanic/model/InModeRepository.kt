package com.onewisebit.scp_scarycontainmentpanic.model

interface InModeRepository {

    fun getMode(id: Int): Mode
    fun insertMode(mode: Mode)
    fun getAllModes():List<Mode>
    fun insertAll(modes:List<Mode>)
}