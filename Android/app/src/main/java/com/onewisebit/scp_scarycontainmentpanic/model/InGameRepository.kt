package com.onewisebit.scp_scarycontainmentpanic.model

interface InGameRepository {
    
    fun insertGame(game: Game)

    fun updateGame(game: Game)

    fun getGameById(id: Long): Game

    fun getType(id: Long): Int

    fun getAllGames(): List<Game>

    fun deleteGame(game: Game)

    fun deleteAllGames()
    
}