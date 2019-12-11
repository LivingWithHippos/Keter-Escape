package com.onewisebit.scpescape.model.repositories

import android.util.Log
import com.onewisebit.scpescape.model.entities.Mode
import com.onewisebit.scpescape.model.daos.ModeDAO
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

class ModeRepository(private val modeDAO: ModeDAO) :
    InModeRepository {

    override fun getMode(id: Int): Single<Mode> = modeDAO.getModeById(id)

    override fun getMinPlayers(id: Int): Int = modeDAO.getMinPlayers(id)

    override fun getMaxPlayers(id: Int): Int = modeDAO.getMaxPlayers(id)

    override fun insertMode(mode: Mode) {
        modeDAO.insertMode(mode)
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.d(TAG, "Insert Success") },
                { Log.d(TAG, "Insert Error") }
            )
    }

    override fun getAllModes(): List<Mode> = modeDAO.getAllModes()

    override suspend fun insertAll(modes: List<Mode>) = modeDAO.insertAll(modes)

    companion object {
        private val TAG = PlayerRepository::class.java.simpleName
    }
}