package com.onewisebit.scpescape.di

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import com.onewisebit.scpescape.model.database.SCPDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SCPApplication : Application() {

    @SuppressLint("CheckResult")
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // use this as Android context
            androidContext(this@SCPApplication)
            // use AndroidLogger as Logger (default Level.INFO)
            androidLogger()
            // module list
            modules(appModule)
        }

        //ping the db so it gets created on the first run
        //TODO: test if it happens
        val db: SCPDatabase by inject()
        db.gameDAO().deleteTemporaryGames().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.d(TAG, "Temporary games deleted successfully") },
                { exc -> Log.d(TAG, "Error while deleting temporary games", exc) }
            )
    }

    companion object {
        private val TAG = SCPApplication::class.java.simpleName
    }
}