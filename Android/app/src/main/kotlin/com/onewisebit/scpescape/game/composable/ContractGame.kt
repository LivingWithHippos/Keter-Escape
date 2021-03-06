package com.onewisebit.scpescape.game.composable

import com.onewisebit.scpescape.fsm.states.StateGame
import com.onewisebit.scpescape.model.entities.*
import com.onewisebit.scpescape.model.parsed.*

/**
 * This is an attempt to create a composable MVP, since most of the game related parts are using similar or the same things.
 * Every model takes a game ID, which is provided once to the presenter constructor and then passed down to the model.
 * This is why the presenters do not have any game Id in their functions (see [PresenterGameImpl] and [ModelGameImpl]).
 *
 * HOW TO:
 * 1. add the needed contract interfaces to the contract of the class we want to implement
 * interface MyPresenter: ContractRound.PresenterRound
 * interface MyModel: ContractRound.ModelRound
 * 2. add to the implemented interfaces the reference to the needed mvp and use them with by
 * MyModelImpl (val roundModel : ModelRound ) : MyModel, ModelRound by roundModel
 * 3. Update the dependency injection of our mvp. Assuming we already have our composable mvp injectable (ModelRound etc.):
 * Old:
 * factory<MyModel> { MyModelImpl() }
 * New:
 * factory<MyModel> { MyModelImpl(get()) }
 *
 * Parameters can be passed to composable classes using parametersOf(args):
 * factory<MyPresenter> { MyPresenterImpl(get{ parametersOf(args) }) }
 */
// TODO: decide if we can compose only the model since most of the times it's what is needed.
//  Change composed presenter to use their composed model and not the composed presenter.
// Problem seems to be that models passed to presenterImpl do not give access to implemented composable models.
// There`s also an option not to extend our model with composable models, since they are accessed only through the presenters.
// Otherwise to remove composable presenters we should pass references of the composable models to our presenter
interface ContractGame {

    interface ViewGame

    //todo: decide how to implement composable mvp to avoid repetition here
    interface PresenterGame {
        suspend fun getGame(): Game
        suspend fun saveGameState(oldState: StateGame, newState: StateGame)
        suspend fun getSave(): Save
        suspend fun saveGame(
            stateMachineOld: String?,
            stateMachineNew: String?,
            player: Long? = null,
            stateProcessed: Boolean = false
        )

        suspend fun saveMachineStates(oldState: String, newState: String)
        suspend fun saveCurrentPlayer(playerID: Long)
        suspend fun saveStateProcessed(processed: Boolean)
    }

    interface ModelGame {
        suspend fun getGame(gameID: Long): Game
        suspend fun setGameEnded(gameID: Long)
        suspend fun getSave(gameID: Long): Save
        suspend fun saveGame(
            gameID: Long,
            stateMachineOld: String?,
            stateMachineNew: String?,
            player: Long? = null,
            stateProcessed: Boolean = false
        )

        suspend fun saveMachineStates(gameID: Long, oldState: String, newState: String)
        suspend fun saveCurrentPlayer(gameID: Long, playerID: Long)
        suspend fun saveStateProcessed(gameID: Long, processed: Boolean)
    }
}

interface ContractParticipant {

    interface PresenterParticipant {
        suspend fun getParticipants(): List<Participant>?
        suspend fun getAliveParticipants(): List<Participant>
        suspend fun getCurrentParticipant(): Participant
        suspend fun getParticipant(playerId: Long): Participant
        suspend fun killParticipantsList(ids: List<Long>, currentRound: Round? = null)
        suspend fun getGroup(playerId: Long): String
        suspend fun getParticipantStates(gameID: Long, playerID: Long): List<State>
        suspend fun getAllParticipantsActiveStates(gameID: Long): List<State>
        suspend fun getAllParticipantsStates(gameID: Long): List<State>
        suspend fun getParticipantsDeadStates(gameID: Long): List<State>
        suspend fun getRoundStates(gameID: Long, roundNumber: Int): List<State>
        suspend fun getActiveRoundStates(gameID: Long, roundNumber: Int): List<State>
        suspend fun insertState(state: State): Long
        suspend fun setStateActive(stateID: Long, active: Boolean)
        suspend fun removeState(state: State)
    }

    interface ModelParticipant {
        suspend fun getParticipants(gameID: Long): List<Participant>?
        suspend fun getParticipant(gameID: Long, playerId: Long): Participant
        suspend fun getCurrentParticipant(gameID: Long): Participant
        suspend fun getMissingRoundParticipants(gameID: Long): List<Participant>
        suspend fun setGameParticipantRole(gameID: Long, playerID: Long, roleName: String)
        suspend fun setParticipantState(gameID: Long, playerID: Long, state: Int)
        suspend fun getGroup(gameID: Long, playerId: Long): String
        suspend fun getParticipantStates(gameID: Long, playerID: Long): List<State>
        suspend fun setParticipantStateRound(round: Round, playerID: Long, stateName: String): Long
        suspend fun setParticipantStateTurn(turn: Turn, playerID: Long, stateName: String)
        suspend fun getAllParticipantsActiveStates(gameID: Long): List<State>
        suspend fun getAllParticipantsStates(gameID: Long): List<State>
        suspend fun getParticipantsDeadStates(gameID: Long): List<State>
        suspend fun getRoundStates(gameID: Long, roundNumber: Int): List<State>
        suspend fun getActiveRoundStates(gameID: Long, roundNumber: Int): List<State>
        suspend fun insertState(state: State): Long
        suspend fun setStateActive(stateID: Long, active: Boolean)
        suspend fun removeState(state: State)
    }
}

interface ContractPlayer {

    interface PresenterPlayer {
        suspend fun getPlayers(): List<Player>
        suspend fun getPlayers(participantList: List<Participant>): List<Player>
        suspend fun getPlayer(id: Long): Player
    }

    interface ModelPlayer {
        suspend fun getPlayers(gameID: Long): List<Player>
        suspend fun getPlayer(id: Long): Player?
        suspend fun getPlayerName(playerId: Long): String?
    }
}

interface ContractRound {

    interface PresenterRound {
        suspend fun getRounds(): List<Round>
        suspend fun getCurrentRound(): Round?
        suspend fun getCurrentRoundDetails(): RoundDetails
        suspend fun getRoundDetail(roundCode: String): RoundDetails
        suspend fun getAllModeDetails(): List<RoundDetails>?
        suspend fun addRound()
        suspend fun setCurrentRoundReplayable()
        suspend fun isCurrentRoundReplayable(): Boolean
    }

    interface ModelRound {
        suspend fun getRounds(gameID: Long): List<Round>
        suspend fun getLastRound(gameID: Long): Round?
        suspend fun getRoundDetail(modeId: Int, roundCode: String): RoundDetails
        suspend fun getAllModeDetails(modeId: Int): List<RoundDetails>?
        suspend fun addRound(
            gameID: Long,
            details: String,
            roundNumber: Int = -1,
            replay: Boolean = false
        )

        suspend fun addRound(gameID: Long)
        suspend fun getRoundsMode(gameId: Long): Int
        suspend fun setRoundReplay(gameId: Long, roundNumber: Int, replay: Boolean)
        suspend fun getRoundReplay(gameID: Long, num: Int): Boolean
    }
}

interface ContractTurn {

    interface PresenterTurn {
        suspend fun getTurns(): List<Turn>
        suspend fun getRoundTurns(roundNumber: Int): List<Turn>?
        suspend fun getLatestRoundTurns(): List<Turn>?
        suspend fun getLatestTurn(): Turn
        suspend fun isLastTurn(): Boolean
        suspend fun addTurn(playerId: Long): Int
    }

    interface ModelTurn {
        suspend fun getTurns(gameID: Long): List<Turn>
        suspend fun getRoundTurns(gameID: Long, roundNumber: Int): List<Turn>?
        suspend fun getLatestRoundTurns(gameID: Long): List<Turn>?
        suspend fun getLatestTurn(gameID: Long): Turn?
        suspend fun addTurn(gameID: Long, playerId: Long): Int
        suspend fun getMissingTurnsParticipants(gameID: Long): List<Long>?
    }
}

interface ContractAction {

    interface ModelAction {
        suspend fun getTemplates(): List<TurnAction>
        suspend fun getCompleteAction(modeId: Int, roleName: String, roundCode: String): TurnAction
        suspend fun getRoleDetails(modeId: Int, roleName: String): RoleDetails
        suspend fun getModeActions(modeId: Int): List<TurnAction>
        suspend fun getModeId(gameId: Long): Int
        suspend fun getPartialAction(modeId: Int, roleName: String, roundName: String): TurnAction
    }

    interface PresenterAction {
        suspend fun getAction(roleName: String, roundCode: String): TurnAction
        suspend fun getRoleDetails(roleName: String): RoleDetails
        suspend fun getPartialAction(roleName: String, roundName: String): TurnAction
        suspend fun getModeActions(): List<TurnAction>
    }
}

interface ContractVote {

    interface ModelVote {
        suspend fun getGameVotes(gameId: Long): List<Vote>
        suspend fun getRoundVotes(gameId: Long, roundNumber: Int): List<Vote>
        suspend fun getLastRoundVotes(gameId: Long): List<Vote>
        suspend fun getCurrentTurnVotes(gameId: Long): List<Vote>
        suspend fun addCurrentTurnVotes(gameID: Long, votedPlayerId: Long, voteType: String)
        suspend fun addVote(
            gameId: Long,
            roundNumber: Int,
            turnNumber: Int,
            playerID: Long,
            votedPlayerID: Long,
            voteType: String
        )

        suspend fun removeCurrentTurnVote(gameId: Long, votedPlayerId: Long)
    }

    interface PresenterVote {
        suspend fun getLastRoundVotes(): List<Vote>
        suspend fun getCurrentPlayerVotes(): List<Vote>
        suspend fun addCurrentRoundVote(votedPlayerId: Long, voteType: String)
        suspend fun removeCurrentTurnVote(votedPlayerId: Long)
    }
}

interface ContractMode {
    interface PresenterMode {
        suspend fun getMode(): ModeDataClass
        suspend fun getVictoryConditions(): List<VictoryCondition>
    }

    interface ModelMode {
        suspend fun getMode(gameID: Long): ModeDataClass?
        suspend fun getVictoryConditions(gameID: Long): List<VictoryCondition>
    }
}