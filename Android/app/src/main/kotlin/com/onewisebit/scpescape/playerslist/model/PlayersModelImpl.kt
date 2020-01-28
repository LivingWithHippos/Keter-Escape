package com.onewisebit.scpescape.playerslist.model

import com.onewisebit.scpescape.model.entities.Participant
import com.onewisebit.scpescape.model.entities.Player
import com.onewisebit.scpescape.model.repositories.InGameRepository
import com.onewisebit.scpescape.model.repositories.InParticipantRepository
import com.onewisebit.scpescape.model.repositories.InPlayerRepository
import com.onewisebit.scpescape.playerslist.PlayersContract
import com.onewisebit.scpescape.utilities.PARTICIPANT_STATE_ALIVE
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class PlayersModelImpl(
    playerRepository: InPlayerRepository,
    participantRepository: InParticipantRepository,
    gameRepository: InGameRepository
) : PlayersContract.PlayersModel {

    private var playerRepo: InPlayerRepository = playerRepository
    private var participantRepo: InParticipantRepository = participantRepository
    private var gameRepo: InGameRepository = gameRepository

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
            Participant(gameID, playerID, null, PARTICIPANT_STATE_ALIVE)
        )
    }

    override fun removeGameParticipant(gameID: Long, playerID: Long): Completable {
        return participantRepo.removeParticipant(gameID, playerID)
    }

    override fun setTemporary(gameID: Long, isTemp: Boolean): Completable =
        gameRepo.setTemporary(gameID, isTemp)
}