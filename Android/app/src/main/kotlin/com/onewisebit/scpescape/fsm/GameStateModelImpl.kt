package com.onewisebit.scpescape.fsm

import com.onewisebit.scpescape.model.entities.*
import com.onewisebit.scpescape.model.repositories.*
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

class GameStateModelImpl(gameRepository: InGameRepository,
                         participantRepository: InParticipantRepository,
                         playerRepository: InPlayerRepository,
                         roundRepository: InRoundRepository,
                         turnRepository: InTurnRepository,
                         modeRepository: InModeRepository
) : GameStateContract.GameStateModel {

    private var gameRepo: InGameRepository = gameRepository
    private var participantRepo: InParticipantRepository = participantRepository
    private var playerRepo: InPlayerRepository = playerRepository
    private var roundRepo: InRoundRepository = roundRepository
    private var turnRepo: InTurnRepository = turnRepository
    private var modeRepo: InModeRepository = modeRepository

    override fun getGame(gameID: Long): Single<Game> {
        return gameRepo.getGameById(gameID)
    }

    override fun getParticipants(gameID: Long): Flowable<List<Participant>> {
        return participantRepo.getGameParticipants(gameID)
    }

    override fun getPlayers(gameID: Long): Single<List<Player>> {
        return participantRepo.getGamePlayers(gameID)
    }

    override fun getRounds(gameID: Long): Flowable<List<Round>> {
        return roundRepo.getRounds(gameID)
    }

    override fun getTurns(gameID: Long): Flowable<List<Turn>> {
        return turnRepo.getGameTurns(gameID)
    }

    override fun getMode(gameID: Long): Single<Mode> {
        return gameRepo.getMode(gameID)
    }
}