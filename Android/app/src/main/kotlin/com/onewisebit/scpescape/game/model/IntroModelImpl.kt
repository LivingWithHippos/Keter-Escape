package com.onewisebit.scpescape.game.model

import com.onewisebit.scpescape.game.IntroContract
import com.onewisebit.scpescape.model.repositories.*

class IntroModelImpl(
    gameRepository: InGameRepository,
    participantRepository: InParticipantRepository,
    playerRepository: InPlayerRepository,
    roundRepository: InRoundRepository,
    turnRepository: InTurnRepository,
    modeRepository: InModeJSONRepository) :
    GameModelImpl(gameRepository,participantRepository,playerRepository,roundRepository,turnRepository,modeRepository),
    IntroContract.IntroModel {

    override suspend fun assignRole(gameID: Long, playerID: Long, roleName: String) =
        participantRepository.setGameParticipantRole(gameID, playerID, roleName)
}