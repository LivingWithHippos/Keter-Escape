package com.onewisebit.scpescape.model.repositories

import com.onewisebit.scpescape.model.parsed.ModeDataClass

interface InModeJSONRepository {

    suspend fun getMode(id: Int): ModeDataClass?
    suspend fun getAllModes(): List<ModeDataClass>?
}