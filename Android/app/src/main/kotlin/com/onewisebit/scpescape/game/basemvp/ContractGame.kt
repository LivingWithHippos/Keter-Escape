package com.onewisebit.scpescape.game.basemvp

import com.onewisebit.scpescape.model.entities.*
import com.onewisebit.scpescape.model.parsed.ModeDataClass
import com.onewisebit.scpescape.model.parsed.RoundDetails

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
// TODO: decide if we can compose only the model since most of the times it's what is needed. Change composed presenter to use their composed model and not the composed presenter.
interface ContractGame {

    interface ViewGame

    interface PresenterGame {
        suspend fun getGame(): Game
    }

    interface ModelGame {
        suspend fun getGame(gameID: Long): Game
    }
}

interface ContractParticipant {

    interface PresenterParticipant {
        suspend fun getParticipants(): List<Participant>?
        suspend fun getAliveParticipants(): List<Participant>
        suspend fun getCurrentParticipant(): Participant
    }

    interface ModelParticipant {
        suspend fun getParticipants(gameID: Long): List<Participant>?
        suspend fun getCurrentParticipant(gameID: Long): Participant
        suspend fun getMissingRoundParticipants(gameID: Long): List<Participant>
    }
}

interface ContractPlayer {

    interface PresenterPlayer {
        suspend fun getPlayers(): List<Player>
    }

    interface ModelPlayer {
        suspend fun getPlayers(gameID: Long): List<Player>
        suspend fun getPlayerName(playerId: Long): String?
    }
}

interface ContractRound {

    interface PresenterRound {
        suspend fun getRounds(): List<Round>
        suspend fun getCurrentRound(): Round
        suspend fun getCurrentRoundDetails(): RoundDetails?
        suspend fun getRoundDetail(roundCode: String): RoundDetails?
        suspend fun getAllModeDetails(): List<RoundDetails>?
        suspend fun addRound(details: String)
    }

    interface ModelRound {
        suspend fun getRounds(gameID: Long): List<Round>
        suspend fun getRoundDetail(modeId: Int, roundCode: String): RoundDetails?
        suspend fun getAllModeDetails(modeId: Int): List<RoundDetails>?
        suspend fun addRound(gameID: Long, details: String)
        suspend fun getRoundsMode(gameId: Long): Int
    }
}

interface ContractTurn {

    interface PresenterTurn {
        suspend fun getTurns(): List<Turn>
        suspend fun getRoundTurns(roundNumber: Int): List<Turn>?
        suspend fun getLatestRoundTurns(): List<Turn>?
        suspend fun isLastTurn(): Boolean
        suspend fun addTurn(playerId: Long) : Int
    }

    interface ModelTurn {
        suspend fun getTurns(gameID: Long): List<Turn>
        suspend fun getRoundTurns(gameID: Long, roundNumber: Int): List<Turn>?
        suspend fun getLatestRoundTurns(gameID : Long): List<Turn>?
        suspend fun addTurn(gameID : Long, playerId: Long) : Int
        suspend fun getMissingTurnsParticipants(gameID : Long) : List<Long>?
    }
}

interface ContractMode {
    interface PresenterMode {
        suspend fun getMode(): ModeDataClass
    }

    interface ModelMode {
        suspend fun getMode(gameID: Long): ModeDataClass?
    }
}