package com.onewisebit.scpescape.model.repositories

import android.util.Log
import com.onewisebit.scpescape.model.entities.Mode
import com.onewisebit.scpescape.model.daos.ModeDAO
import io.reactivex.android.schedulers.AndroidSchedulers

class ModeRepository(private val modeDAO: ModeDAO) :
    InModeRepository {

    override fun getMode(id: Long): Mode = modeDAO.getModeById(id)

    override fun getMinPlayers(id: Long): Int = modeDAO.getMinPlayers(id)

    override fun getMaxPlayers(id: Long): Int = modeDAO.getMaxPlayers(id)

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