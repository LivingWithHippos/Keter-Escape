package com.onewisebit.scpescape.model.repositories

import android.annotation.SuppressLint
import android.util.Log
import com.onewisebit.scpescape.model.daos.PlayerDAO
import com.onewisebit.scpescape.model.entities.Player
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
class PlayerRepository(private val playerDAO: PlayerDAO) :
    InPlayerRepository {

    override fun updatePlayer(player: Player) {
        playerDAO.updatePlayer(player)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.d(TAG, "Update Success") },
                { Log.d(TAG, "Update Error") }
            )
    }

    override fun insertPlayer(player: Player) {
        playerDAO.insertPlayer(player)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.d(TAG, "Insert Success") },
                { Log.d(TAG, "Insert Error") }
            )
    }


    override fun getPlayerById(id: String): Single<Player> = playerDAO.getPlayerById(id)

    override fun getAllPlayers(): Flowable<List<Player>> = playerDAO.getAllPlayers()

    override fun getPlayersByName(name: String): Flowable<List<Player>> =
        playerDAO.getPlayersByName(name)

    override suspend fun getPlayersByGame(gameID: Long): List<Player> =
        playerDAO.getPlayersByGame(gameID)

    override fun deleteAllPlayers() {
        Completable.fromAction { playerDAO.deleteAllPlayers() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.d(TAG, "Delete all Success") },
                { Log.d(TAG, "Delete all Error") }
            )
    }

    companion object {
        private val TAG = PlayerRepository::class.java.simpleName
    }
}