package com.onewisebit.scpescape.model.repositories

import com.onewisebit.scpescape.model.parsed.RoundDetails
import com.onewisebit.scpescape.model.parsed.RoundInformation

interface RoundDetailRepository {

    suspend fun getRoundDetail(modeId: Int, roundCode: String): RoundInformation?
    suspend fun getAllDetails(modeId: Int): List<RoundInformation>?
    suspend fun getAllRoundsDetails(): List<RoundDetails>?
}