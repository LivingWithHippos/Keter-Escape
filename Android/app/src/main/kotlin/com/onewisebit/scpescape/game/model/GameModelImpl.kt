package com.onewisebit.scpescape.game.model

import com.onewisebit.scpescape.game.GameContract
import com.onewisebit.scpescape.model.entities.*
import com.onewisebit.scpescape.model.repositories.GameRepository
import com.onewisebit.scpescape.model.repositories.ParticipantRepository
import io.reactivex.Flowable
import io.reactivex.Single

class GameModelImpl(gameRepository: GameRepository,participantRepository: ParticipantRepository): GameContract.GameModel {

    private val partRepo = participantRepository
    private val gameRepo = gameRepository

    override fun getGame(gameID: Long): Single<Game> =
        gameRepo.getGameById(gameID)

    override fun getMode(gameID: Long): Single<Mode> =
        gameRepo.getMode(gameID)

    override fun getParticipant(gameID: Long): Flowable<List<Participant>> =
        partRepo.getGameParticipants(gameID)

    override fun getPlayers(gameID: Long): Flowable<List<Player>> =
        partRepo.getPlayers(gameID)

    override fun getRoles(gameID: Long): Flowable<List<Role>> =
        partRepo.getRoles(gameID)
}