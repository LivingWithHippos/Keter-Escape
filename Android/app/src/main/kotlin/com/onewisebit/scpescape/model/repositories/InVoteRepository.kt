package com.onewisebit.scpescape.model.repositories

import com.onewisebit.scpescape.model.entities.Vote
import io.reactivex.Completable
import io.reactivex.Flowable

interface InVoteRepository {

    suspend fun insertVote(vote: Vote)

    suspend fun getGameVotes(gameID: Long): List<Vote>

    suspend fun getRoundVotes(gameID: Long, roundNumber: Int): List<Vote>

    suspend fun getRoundVotedPlayersId(gameID: Long, roundNumber: Int, type: String): List<Long>
}