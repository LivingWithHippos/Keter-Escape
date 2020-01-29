package com.onewisebit.scpescape.game.presenter

import com.onewisebit.scpescape.game.GameContract
import com.onewisebit.scpescape.model.ModeDataClass
import com.onewisebit.scpescape.model.RolesDetail
import com.onewisebit.scpescape.model.entities.*
import com.onewisebit.scpescape.utilities.PARTICIPANT_STATE_ALIVE
import kotlin.random.Random

class GamePresenterImpl(
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

    override suspend fun assignRoles() {
        // TODO: move this to intro presenter, must not happen after the first time
        //if it's a game in progress this doesn't need to be called or we can check from here directly
        val participants: List<Participant> = gameModel.getParticipants(gameID)
        val mode: ModeDataClass? = gameModel.getMode(gameID)
        if (mode != null) {
            // get roles division for this mode
            val roles: List<RolesDetail> =
                mode.rolesDivision.filter { participants.size >= it.minPlayers && participants.size <= it.maxPlayers }[0].roles
            // for simplicity we build a string array with every necessary role
            val possibleRoles: ArrayList<String> = ArrayList()
            roles.filter{!it.default}.forEach{ details ->
                for (i in 0 until details.quantity)
                    possibleRoles.add(details.role)
            }
            // special role are fixed for the players range (for now)
            // the rest of the participants will have the default role
            val defaultRole = roles.filter { it.default }[0]
            while (possibleRoles.size < participants.size)
                possibleRoles.add(defaultRole.role)

            // assigning the various roles in the db
            participants.forEach {
                val roleName = possibleRoles.removeAt(Random.nextInt(possibleRoles.size))
                gameModel.assignRole(gameID, it.playerID, roleName)
            }

        }
        else
            throw IllegalArgumentException("GamePresenter couldn't get the mode from game $gameID to assign roles")
    }

    override suspend fun isLastTurn(): Boolean {
        return gameModel.getMissingRoundParticipants(gameID).isEmpty()
    }

    override fun onDestroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}