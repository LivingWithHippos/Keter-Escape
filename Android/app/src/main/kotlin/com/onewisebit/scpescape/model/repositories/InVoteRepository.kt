package com.onewisebit.scpescape.model.repositories

import com.onewisebit.scpescape.model.entities.Vote

interface InVoteRepository {

    suspend fun insertVote(vote: Vote)

    suspend fun insertCurrentTurnVote(gameID: Long, votedPlayerId: Long, voteType: String)

    suspend fun getGameVotes(gameID: Long): List<Vote>

    suspend fun getRoundVotes(gameID: Long, roundNumber: Int): List<Vote>

    // todo: check if these two methods are the same
    suspend fun getCurrentPlayerVotes(gameID: Long): List<Vote>

    suspend fun getLastRoundVotes(gameID: Long): List<Vote>

    suspend fun getRoundVotedPlayersId(gameID: Long, roundNumber: Int, type: String): List<Long>

    suspend fun removeVote(
        gameId: Long,
        roundNumber: Int,
        turnNumber: Int,
        playerID: Long,
        votedPlayerId: Long
    )

    suspend fun removeCurrentTurnVote(
        gameId: Long,
        votedPlayerId: Long
    )
}

