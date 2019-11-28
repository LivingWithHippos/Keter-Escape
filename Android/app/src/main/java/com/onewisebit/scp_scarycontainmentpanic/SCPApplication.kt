package com.onewisebit.scp_scarycontainmentpanic

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SCPApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            // use this as Android context
            androidContext(this@SCPApplication)
            // use AndroidLogger as Logger (default Level.INFO)
            androidLogger()
            // module list
            modules(appModule)
        }
    }
}