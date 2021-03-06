package com.onewisebit.scpescape.model.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.onewisebit.scpescape.model.entities.Vote

@Dao
interface VoteDAO {
    /**
     * Insert a Vote in the database. If the vote already exists, replace it.
     * @param vote the Turn to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVote(vote: Vote)

    /**
     * Get all of the votes from a game.
     * @return the list of votes from the table with a specific game id.
     */
    @Query("SELECT * FROM votes WHERE votes.game = :gameID")
    suspend fun getGameVotes(gameID: Long): List<Vote>


    /**
     * Get all of the votes from a round.
     * @return the list of votes from the table with a specific game id and round number.
     */
    @Query("SELECT * FROM votes WHERE votes.game = :gameID AND votes.round = :roundNumber")
    suspend fun getRoundVotes(gameID: Long, roundNumber: Int): List<Vote>

    /**
     * Get all of the votes from the last round.
     * @return the list of votes from the table with a specific game id and round number.
     */
    @Query("SELECT * FROM votes WHERE votes.game = :gameID AND votes.round = (SELECT MAX(rounds.number) FROM rounds WHERE rounds.game = :gameID)")
    suspend fun getLastRoundVotes(gameID: Long): List<Vote>


    /**
     * Get all of all the votes from the current player/participant (last turn).
     * @return the list of votes from the table with a specific game id
     */
    @Query("SELECT * FROM votes WHERE votes.game = :gameID AND votes.turn = (SELECT MAX(turn_number) FROM turns WHERE turns.game = :gameID)")
    suspend fun getCurrentPlayerVotes(gameID: Long): List<Vote>

    /**
     * Get all of the votes from a round.
     * @return the list of Votes from the table with a specific game id, round number and vote action
     */
    @Query("SELECT * FROM votes WHERE votes.game = :gameID AND votes.round = :roundNumber AND votes.vote_action = :action")
    suspend fun getRoundVotes(gameID: Long, roundNumber: Int, action: String): List<Vote>


    /**
     * Get all of the voted players from a round.
     * @return the list of voted players id from the table with a specific game id, round number and vote action.
     */
    @Query("SELECT votes.player_voted FROM votes WHERE votes.game = :gameID AND votes.round = :roundNumber AND votes.vote_action = :action")
    suspend fun getRoundVotedPlayersId(gameID: Long, roundNumber: Int, action: String): List<Long>

    /**
     * Get all of the voted players from a round.
     * @return the list of voted players id from the table with a specific game id and round number.
     */
    @Query("SELECT votes.player_voted FROM votes WHERE votes.game = :gameID AND votes.round = :roundNumber")
    suspend fun getRoundVotedPlayersId(gameID: Long, roundNumber: Int): List<Long>

    @Query("DELETE FROM votes WHERE votes.game = :gameId AND votes.round = :roundNumber AND votes.turn = :turnNumber AND votes.player = :playerID AND votes.player_voted = :votedPlayerId")
    suspend fun removeVote(
        gameId: Long,
        roundNumber: Int,
        turnNumber: Int,
        playerID: Long,
        votedPlayerId: Long
    )
}