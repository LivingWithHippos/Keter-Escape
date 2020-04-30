package com.onewisebit.scpescape.model.repositories

import com.onewisebit.scpescape.model.entities.Save

interface InSaveRepository {

    suspend fun insertSave(save: Save)

    suspend fun getSave(gameID: Long): Save

    suspend fun getAllSaves(): List<Save>

    suspend fun removeSave(save: Save)

    fun deleteAllSaves()

    suspend fun setMachineStates(gameID: Long, oldState: String, newState: String)

    suspend fun setCurrentPlayer(gameID: Long, playerID: Long)

    suspend fun setStateProcessed(gameID: Long, processed: Boolean)

}