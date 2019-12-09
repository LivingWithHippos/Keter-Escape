package com.onewisebit.scp_scarycontainmentpanic.model

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PlayerRepository(private val playerDAO: PlayerDAO) : InPlayerRepository {

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


    override fun getPlayerById(id: String): Flowable<Player> = playerDAO.getPlayerById(id)

    override fun getAllPlayers(): Flowable<List<Player>> = playerDAO.getAllPlayers()

    override fun getPlayersByName(name:String): Flowable<List<Player>> = playerDAO.getPlayersByName(name)

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