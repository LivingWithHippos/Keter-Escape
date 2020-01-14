package com.onewisebit.scpescape.fsm

import com.onewisebit.scpescape.model.entities.*
import com.onewisebit.scpescape.model.repositories.*

class GameStateModelImpl(gameRepository: InGameRepository,
                         participantRepository: InParticipantRepository,
                         playerRepository: InPlayerRepository,
                         roundRepository: InRoundRepository,
                         turnRepository: InTurnRepository,
                         modeRepository: InModeRepository
) : GameStateContract.GameStateModel {

    private var gameRepo: InGameRepository = gameRepository
    private var modeRepo: InModeRepository = modeRepository

    override fun getGame(gameID: Long): Game {
        gameRepo.getGameById(gameID).
    }

    override fun getParticipants(gameID: Long): List<Participant> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPlayers(gameID: Long): List<Player> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getRounds(gameID: Long): List<Round> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTurns(gameID: Long): List<Turn> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMode(gameID: Long): Mode {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}