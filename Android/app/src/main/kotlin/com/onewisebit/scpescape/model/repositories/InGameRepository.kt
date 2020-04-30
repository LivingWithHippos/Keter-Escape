package com.onewisebit.scpescape.model.repositories

import com.onewisebit.scpescape.model.entities.Game
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface InGameRepository {

    fun insertGame(game: Game): Single<Long>

    fun updateGame(game: Game)

    fun getGameById(id: Long): Single<Game>

    fun getUnfinishedGames(): Flowable<List<Game>>

    suspend fun getGameBlocking(id: Long): Game

    fun getType(id: Long): Int

    suspend fun getModeId(id: Long): Int

    fun getAllGames(): List<Game>

    suspend fun endGame(id: Long)

    fun deleteGame(game: Game)

    fun deleteAllGames()

    fun setTemporary(gameID: Long, isTemp: Boolean): Completable

}