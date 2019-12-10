package com.onewisebit.scp_scarycontainmentpanic.model

import android.util.Log
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GameRepository(private val gameDAO: GameDAO) : InGameRepository {

    override fun getGameById(id: Long): Game = gameDAO.getGameById(id)

    override fun getType(id: Long): Int = gameDAO.getType(id)

    override fun getAllGames(): List<Game> = gameDAO.getAllGames()

    override fun deleteGame(game: Game) = gameDAO.removeGame(game)

    override fun deleteAllGames() = gameDAO.deleteAllGames()

    override fun insertGame(game: Game): Single<Long> {
        return Single.fromCallable<Long> {gameDAO.insertGame(game)}
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

    companion object {
        private val TAG = PlayerRepository::class.java.simpleName
    }
}