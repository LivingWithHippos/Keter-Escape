package com.onewisebit.scpescape.model.daos

import androidx.room.*
import com.onewisebit.scpescape.model.entities.Participant
import com.onewisebit.scpescape.model.entities.State
import com.onewisebit.scpescape.utilities.STATE_DEAD
import io.reactivex.Completable

@Dao
interface StateDAO {

    /**
     * Get the list of states for a participant
     * @return a List of States from the table with a specific game and player id.
     */
    @Query("SELECT * FROM states WHERE game = :gameID AND affected_player = :playerID")
    suspend fun getParticipantStates(gameID: Long, playerID: Long): List<State>?

    /**
     * Get the list of states active for a participant
     * @return a List of States from the table with a specific game and player id and currently active.
     */
    @Query("SELECT * FROM states WHERE game = :gameID AND affected_player = :playerID AND active = 1")
    suspend fun getParticipantActiveStates(gameID: Long, playerID: Long): List<State>?

    /**
     * Get the list of active states in a game
     * @return a List of States from the table with a specific game and player id, currently active.
     */
    @Query("SELECT * FROM states WHERE game = :gameID AND active = 1")
    suspend fun getAllGameActiveStates(gameID: Long): List<State>?

    /**
     * Get the list of states in a game
     * @return a List of States from the table with a specific game id.
     */
    @Query("SELECT * FROM states WHERE game = :gameID")
    suspend fun getAllGameStates(gameID: Long): List<State>?

    /**
     * Get the list of active dead states in a game.
     * @return a List of States from the table with a specific game, currently active, type -> dead.
     */
    @Query("SELECT * FROM states WHERE game = :gameID AND active = 1 AND states.name = :name")
    suspend fun getGameDeadStates(gameID: Long, name: String = STATE_DEAD): List<State>?

    /**
     * Get the list of active states with a certain name in a game.
     * @return a List of States from the table with a specific game, currently active, type -> passed type.
     */
    @Query("SELECT * FROM states WHERE game = :gameID AND active = 1 AND states.name = :name")
    suspend fun getGameStatesByName(gameID: Long, name: String): List<State>?

    /**
     * Get the list of states in a round
     * @return a List of States from the table with a specific game id and round number.
     */
    @Query("SELECT * FROM states WHERE game = :gameID AND round =:roundNumber")
    suspend fun getRoundStates(gameID: Long, roundNumber: Int): List<State>?

    /**
     * Get the list of active states in a round
     * @return a List of States from the table with a specific game id and round number.
     */
    @Query("SELECT * FROM states WHERE game = :gameID AND round =:roundNumber AND active = 1")
    suspend fun getActiveRoundStates(gameID: Long, roundNumber: Int): List<State>?

    /**
     * Insert a State in the database. If the State already exists, replace it.
     * @param state the State to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertState(state: State): Long

    /**
     * Set if a state is active or not
     */
    @Query("UPDATE states SET active = :active WHERE states.id = :stateID")
    suspend fun setStateActive(stateID: Long, active: Boolean)

    /**
     * Remove a state from the database.
     */
    @Delete
    suspend fun removeParticipant(state: State)
}