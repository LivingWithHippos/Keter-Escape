package com.onewisebit.scpescape.playerslist.model

import com.onewisebit.scpescape.model.entities.Participant
import com.onewisebit.scpescape.model.entities.Player
import com.onewisebit.scpescape.model.repositories.GameRepository
import com.onewisebit.scpescape.model.repositories.ParticipantRepository
import com.onewisebit.scpescape.model.repositories.PlayerRepository
import com.onewisebit.scpescape.playerslist.PlayersContract
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class PlayersModelImpl(
    playerRepository: PlayerRepository,
    participantRepository: ParticipantRepository,
    gameRepository: GameRepository
) : PlayersContract.PlayersModel {

    private var playerRepo: PlayerRepository = playerRepository
    private var participantRepo: ParticipantRepository = participantRepository
    private var gameRepo: GameRepository = gameRepository

    override fun getAllPlayers(): Flowable<List<Player>> {
        return playerRepo.getAllPlayers()
    }

    override fun getPlayersByName(name: String): Flowable<List<Player>> {
        return playerRepo.getPlayersByName(name)
    }

    override fun getParticipantsIDByGame(gameID: Long): Flowable<List<Long>> {
        return participantRepo.getGameParticipantsID(gameID)
    }

    override fun getParticipantsByGame(gameID: Long): Flowable<List<Participant>> {
        return participantRepo.getGameParticipants(gameID)
    }

    override fun getParticipantsNumber(gameID: Long): Single<Int> {
        return participantRepo.getParticipantNumber(gameID)
    }

    override fun addGameParticipant(gameID: Long, playerID: Long): Completable {
        return participantRepo.insertParticipant(
            Participant(gameID, playerID, null, null)
        )
    }

    override fun removeGameParticipant(gameID: Long, playerID: Long): Completable {
        return participantRepo.removeParticipant(gameID, playerID)
    }

    override fun setTemporary(gameID: Long, isTemp: Boolean): Completable =
        gameRepo.setTemporary(gameID, isTemp)
}