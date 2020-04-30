package com.onewisebit.scpescape.model.repositories

import com.onewisebit.scpescape.model.daos.SaveDAO
import com.onewisebit.scpescape.model.entities.Save

class SaveRepository(private val saveDAO: SaveDAO): InSaveRepository {

    override suspend fun insertSave(save: Save) = saveDAO.insertSave(save)

    override suspend fun getSave(gameID: Long): Save = saveDAO.getSaveByGameId(gameID)

    override suspend fun getAllSaves(): List<Save> = saveDAO.getAllSaves()

    override suspend fun removeSave(save: Save) = saveDAO.removeSave(save)

    override fun deleteAllSaves() = saveDAO.deleteAllSaves()

    override suspend fun setMachineStates(gameID: Long, oldState: String, newState: String) = saveDAO.setMachineStates(gameID, oldState, newState)

    override suspend fun setCurrentPlayer(gameID: Long, playerID: Long) = saveDAO.setCurrentPlayer(gameID, playerID)

    override suspend fun setStateProcessed(gameID: Long, processed: Boolean) = saveDAO.setStateProcessed(gameID, processed)
}