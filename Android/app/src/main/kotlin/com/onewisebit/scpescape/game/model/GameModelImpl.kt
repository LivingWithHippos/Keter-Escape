package com.onewisebit.scpescape.game.model

import com.onewisebit.scpescape.game.GameContract
import com.onewisebit.scpescape.model.ModeDataClass
import com.onewisebit.scpescape.model.entities.*
import com.onewisebit.scpescape.model.repositories.*

open class GameModelImpl(
    val gameRepository: InGameRepository,
    val participantRepository: InParticipantRepository,
    val playerRepository: InPlayerRepository,
    val roundRepository: InRoundRepository,
    val turnRepository: InTurnRepository,
    val modeRepository: InModelNewRepository
) : GameContract.GameModel {

    override suspend fun getGame(gameID: Long): Game = gameRepository.getGameBlocking(gameID)

    override suspend fun getParticipants(gameID: Long): List<Participant> = participantRepository.getGameParticipantsBlocking(gameID)

    override suspend fun getPlayers(gameID: Long): List<Player> = playerRepository.getPlayersByGame(gameID)

    override suspend fun getRounds(gameID: Long): List<Round> = roundRepository.getRounds(gameID)

    override suspend fun getTurns(gameID: Long): List<Turn> = turnRepository.getGameTurns(gameID)

    override suspend fun getMode(gameID: Long): ModeDataClass? {
        val gameMode = gameRepository.getModeId(gameID)
        return modeRepository.getMode(gameMode)
    }

    override suspend fun getCurrentParticipant(gameID: Long): Participant = turnRepository.getCurrentParticipant(gameID)

    override suspend fun getMissingRoundParticipants(gameID: Long): List<Participant> = roundRepository.getMissingParticipants(gameID)
}