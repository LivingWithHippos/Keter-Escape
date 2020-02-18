package com.onewisebit.scpescape.model.repositories

import com.onewisebit.scpescape.model.daos.TurnDAO
import com.onewisebit.scpescape.model.daos.VoteDAO
import com.onewisebit.scpescape.model.entities.Vote

class VoteRepository(private val voteDAO: VoteDAO, private val turnDAO: TurnDAO) :
    InVoteRepository {

    override suspend fun insertVote(vote: Vote) = voteDAO.insertVote(vote)

    override suspend fun insertCurrentTurnVote(
        gameID: Long,
        votedPlayerId: Long,
        voteType: String
    ) {
        val turn = turnDAO.getLastTurn(gameID)
        turn?.let {
            insertVote(
                Vote(
                    0,
                    gameID,
                    it.roundNumber,
                    it.turnNumber,
                    it.playerID,
                    votedPlayerId,
                    voteType
                )
            )
        }
    }

    override suspend fun getGameVotes(gameID: Long): List<Vote> = voteDAO.getGameVotes(gameID)

    override suspend fun getRoundVotes(gameID: Long, roundNumber: Int): List<Vote> =
        voteDAO.getRoundVotes(gameID, roundNumber)

    override suspend fun getLastRoundVotes(gameID: Long): List<Vote> =
        voteDAO.getLastRoundVotes(gameID)

    override suspend fun getCurrentPlayerVotes(gameID: Long): List<Vote> =
        voteDAO.getCurrentPlayerVotes(gameID)

    override suspend fun getRoundVotedPlayersId(
        gameID: Long,
        roundNumber: Int,
        type: String
    ): List<Long> = voteDAO.getRoundVotedPlayersId(gameID, roundNumber, type)

    override suspend fun removeCurrentTurnVote(
        gameId: Long,
        votedPlayerId: Long
    ) {
        val turn = turnDAO.getLastTurn(gameId)
        turn?.let {
            removeVote(
                gameId,
                it.roundNumber,
                it.turnNumber,
                it.playerID,
                votedPlayerId
            )
        }

    }

    override suspend fun removeVote(
        gameId: Long,
        roundNumber: Int,
        turnNumber: Int,
        playerID: Long,
        votedPlayerId: Long
    ) {
        voteDAO.removeVote(
            gameId,
            roundNumber,
            turnNumber,
            playerID,
            votedPlayerId
        )
    }
}