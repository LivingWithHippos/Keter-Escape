package com.onewisebit.scpescape.model.repositories

import com.onewisebit.scpescape.model.entities.Game
import com.onewisebit.scpescape.model.entities.Mode
import io.reactivex.Completable
import io.reactivex.Single

interface InGameRepository {

    fun insertGame(game: Game): Single<Long>

    fun updateGame(game: Game)

    fun getGameById(id: Long): Single<Game>

    fun getType(id: Long): Int

    fun getMode(id: Long): Single<Mode>

    fun getAllGames(): List<Game>

    fun deleteGame(game: Game)

    fun deleteAllGames()

    fun setTemporary(gameID: Long, isTemp: Boolean): Completable

}