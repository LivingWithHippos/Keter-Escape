package com.onewisebit.scpescape.game.composable.model

import com.onewisebit.scpescape.game.composable.ContractVote
import com.onewisebit.scpescape.model.entities.Vote
import com.onewisebit.scpescape.model.repositories.InVoteRepository

class ModelVoteImpl(val voteRepository: InVoteRepository) : ContractVote.ModelVote {

    override suspend fun getGameVotes(gameId: Long): List<Vote> =
        voteRepository.getGameVotes(gameId)

    override suspend fun getRoundVotes(gameId: Long, roundNumber: Int): List<Vote> =
        voteRepository.getRoundVotes(gameId, roundNumber)

    override suspend fun getLastRoundVotes(gameId: Long): List<Vote> =
        voteRepository.getLastRoundVotes(gameId)

    override suspend fun getCurrentTurnVotes(gameId: Long): List<Vote> =
        voteRepository.getCurrentPlayerVotes(gameId)

    override suspend fun addCurrentTurnVotes(gameID: Long, votedPlayerId: Long, voteType: String) =
        voteRepository.insertCurrentTurnVote(gameID, votedPlayerId, voteType)

    override suspend fun addVote(gameId: Long, roundNumber: Int,turnNumber: Int,playerID: Long,votedPlayerID: Long,voteType: String) =
        voteRepository.insertVote(Vote(0,gameId,roundNumber,turnNumber,playerID,votedPlayerID,voteType))

    override suspend fun removeCurrentTurnVote(gameId: Long, votedPlayerId: Long) {
        voteRepository.removeCurrentTurnVote(gameId, votedPlayerId)
    }

}