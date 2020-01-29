package com.onewisebit.scpescape.game.presenter

import com.onewisebit.scpescape.game.GameContract
import com.onewisebit.scpescape.model.parsed.ModeDataClass
import com.onewisebit.scpescape.model.entities.*
import com.onewisebit.scpescape.utilities.PARTICIPANT_STATE_ALIVE

open class GamePresenterImpl(
    val gameView: GameContract.GameView,
    val gameModel: GameContract.GameModel,
    val gameID: Long
) : GameContract.GamePresenter {

    override suspend fun getGame(): Game = gameModel.getGame(gameID)

    override suspend fun getParticipants(): List<Participant> = gameModel.getParticipants(gameID)

    override suspend fun getAliveParticipants(): List<Participant> {
        return getParticipants().filter { it.stateValue== PARTICIPANT_STATE_ALIVE }
    }

    override suspend fun getCurrentParticipant(): Participant = gameModel.getCurrentParticipant(gameID)

    override suspend fun getPlayers(): List<Player> = gameModel.getPlayers(gameID)

    override suspend fun getRounds(): List<Round> = gameModel.getRounds(gameID)

    override suspend fun getCurrentRound(): Round {
        return getRounds().maxBy { it.num } ?: throw IllegalStateException("GamePresenter couldn't get last round from game $gameID")
    }

    override suspend fun getTurns(): List<Turn> = gameModel.getTurns(gameID)

    override suspend fun getRoundTurns(roundNumber: Int): List<Turn> = gameModel.getTurns(gameID)

    override suspend fun getMode(): ModeDataClass {
        return gameModel.getMode(gameID) ?: throw java.lang.IllegalStateException("GamePresenter couldn't get the mode from game $gameID")
    }

    override suspend fun isLastTurn(): Boolean {
        return gameModel.getMissingRoundParticipants(gameID).isEmpty()
    }

    override fun onDestroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}