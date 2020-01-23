package com.onewisebit.scpescape.model.repositories

import com.onewisebit.scpescape.model.daos.VoteDAO
import com.onewisebit.scpescape.model.entities.Vote

class VoteRepository(private val voteDAO: VoteDAO): InVoteRepository {

    override suspend fun insertVote(vote: Vote) = voteDAO.insertVote(vote)

    override suspend fun getGameVotes(gameID: Long): List<Vote> = voteDAO.getGameVotes(gameID)

    override suspend fun getRoundVotes(gameID: Long, roundNumber: Int): List<Vote> = voteDAO.getRoundVotes(gameID, roundNumber)

    override suspend fun getRoundVotedPlayersId(
        gameID: Long,
        roundNumber: Int,
        type: String
    ): List<Long> = voteDAO.getRoundVotedPlayersId(gameID, roundNumber, type)
}