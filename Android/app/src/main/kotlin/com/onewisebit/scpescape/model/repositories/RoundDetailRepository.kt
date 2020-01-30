package com.onewisebit.scpescape.model.repositories

import com.onewisebit.scpescape.model.parsed.Details
import com.onewisebit.scpescape.model.parsed.RoundDetails

interface RoundDetailRepository {

    suspend fun getRoundDetail(modeId: Int, roundCode: String): Details?
    suspend fun getAllDetails(modeId: Int): List<Details>?
    suspend fun getAllRoundsDetails(): List<RoundDetails>?
}