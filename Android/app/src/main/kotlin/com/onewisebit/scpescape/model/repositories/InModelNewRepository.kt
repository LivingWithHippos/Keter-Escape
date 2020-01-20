package com.onewisebit.scpescape.model.repositories

import com.onewisebit.scpescape.model.ModeDataClass

interface InModelNewRepository {

    suspend fun getMode(id: Int): ModeDataClass?
    suspend fun getAllModes(): List<ModeDataClass>?
}