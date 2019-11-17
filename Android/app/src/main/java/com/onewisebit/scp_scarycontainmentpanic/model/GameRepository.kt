package com.onewisebit.scp_scarycontainmentpanic.model

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GameRepository(private val gameDAO: GameDAO):InGameRepository {

    override fun getGameById(id: Long): Game = gameDAO.getGameById(id)

    override fun getType(id: Long): Int = gameDAO.getType(id)

    override fun getMode(id: Long): Int = gameDAO.getMode(id)

    override fun getAllGames(): List<Game> = gameDAO.getAllGames()

    override fun deleteGame(game: Game) = gameDAO.removeGame(game)

    override fun deleteAllGames() = gameDAO.deleteAllGames()

    override fun insertGame(game: Game) {
        gameDAO.insertGame(game)
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.d(TAG, "Insert Success") },
                { Log.d(TAG, "Insert Error") }
            )
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