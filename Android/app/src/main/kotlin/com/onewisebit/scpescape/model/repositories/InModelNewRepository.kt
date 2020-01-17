package com.onewisebit.scpescape.model.repositories

import com.onewisebit.scpescape.model.ModeFromJson

interface InModelNewRepository {

    suspend fun getMode(id: Int): ModeFromJson?
    suspend fun getAllModes(): List<ModeFromJson>?
}