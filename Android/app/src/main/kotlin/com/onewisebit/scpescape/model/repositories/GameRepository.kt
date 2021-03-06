package com.onewisebit.scpescape.model.repositories

import android.annotation.SuppressLint
import android.util.Log
import com.onewisebit.scpescape.model.daos.GameDAO
import com.onewisebit.scpescape.model.entities.Game
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
class GameRepository(private val gameDAO: GameDAO) :
    InGameRepository {

    override fun getGameById(id: Long): Single<Game> = gameDAO.getGameById(id)

    override fun getUnfinishedGames(): Flowable<List<Game>> =
        gameDAO.getUnfinishedGames()


    override suspend fun getGameBlocking(id: Long): Game = gameDAO.getGameByIdBlocking(id)

    override fun getType(id: Long): Int = gameDAO.getType(id)

    override fun getAllGames(): List<Game> = gameDAO.getAllGames()

    override suspend fun endGame(id: Long) = gameDAO.setEndGame(id, true)

    override suspend fun getModeId(id: Long): Int = gameDAO.getModeID(id)

    override fun deleteGame(game: Game) = gameDAO.removeGame(game)

    override fun deleteAllGames() = gameDAO.deleteAllGames()

    override fun insertGame(game: Game): Single<Long> {
        return Single.fromCallable<Long> { gameDAO.insertGame(game) }
    }

    override fun updateGame(game: Game) {
        gameDAO.updateGame(game)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.d(TAG, "Update Success") },
                { Log.d(TAG, "Update Error") }
            )
    }

    override fun setTemporary(gameID: Long, isTemp: Boolean): Completable =
        gameDAO.setTemporary(gameID, isTemp)

    companion object {
        private val TAG = PlayerRepository::class.java.simpleName
    }
}