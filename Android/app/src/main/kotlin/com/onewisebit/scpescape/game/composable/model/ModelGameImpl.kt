package com.onewisebit.scpescape.game.composable.model

import com.onewisebit.scpescape.game.composable.ContractGame
import com.onewisebit.scpescape.model.entities.Game
import com.onewisebit.scpescape.model.entities.Save
import com.onewisebit.scpescape.model.repositories.InGameRepository
import com.onewisebit.scpescape.model.repositories.SaveRepository

class ModelGameImpl(
    val gameRepository: InGameRepository,
    val saveRepository: SaveRepository
) : ContractGame.ModelGame {

    override suspend fun getGame(gameID: Long): Game = gameRepository.getGameBlocking(gameID)

    override suspend fun setGameEnded(gameID: Long) = gameRepository.endGame(gameID)

    override suspend fun getSave(gameID: Long): Save = saveRepository.getSave(gameID)

    override suspend fun saveGame(
        gameID: Long,
        stateMachineOld: String?,
        stateMachineNew: String?,
        player: Long?,
        stateProcessed: Boolean
    ) = saveRepository.insertSave(Save(gameID,stateMachineOld, stateMachineNew, player, stateProcessed))

    override suspend fun saveMachineStates(gameID: Long, oldState: String, newState: String) = saveRepository.setMachineStates(gameID, oldState, newState)

    override suspend fun saveCurrentPlayer(gameID: Long, playerID: Long) = saveRepository.setCurrentPlayer(gameID, playerID)

    override suspend fun saveStateProcessed(gameID: Long, processed: Boolean) = saveRepository.setStateProcessed(gameID, processed)
}