package com.onewisebit.scp_scarycontainmentpanic.model

import com.onewisebit.scp_scarycontainmentpanic.PlayersContract
import io.reactivex.Completable
import io.reactivex.Flowable

class PlayersModelImpl(playerRepository: PlayerRepository,participantRepository: ParticipantRepository):PlayersContract.PlayersModel {

    private var playerRepo:PlayerRepository = playerRepository
    private var participantRepo:ParticipantRepository = participantRepository

    override fun getAllPlayers(): Flowable<List<Player>> {
     return playerRepo.getAllPlayers()
    }

    override fun getPlayersByName(name: String): Flowable<List<Player>> {
        return playerRepo.getPlayersByName(name)
    }

    override fun getParticipantsByGame(gameID: Long): Flowable<List<Long>> {
        return participantRepo.getGameParticipantsID(gameID)
    }

    override fun addGameParticipant(gameID: Long, playerID: Long): Completable {
        return participantRepo.insertParticipant(Participant(gameID,playerID,null,null))
    }

    override fun removeGameParticipant(gameID: Long, playerID: Long) {
        return participantRepo.removeParticipant(gameID, playerID)
    }
}