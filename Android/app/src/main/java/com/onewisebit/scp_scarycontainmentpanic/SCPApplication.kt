package com.onewisebit.scp_scarycontainmentpanic

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SCPApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@SCPApplication)
            androidLogger()
            modules(appModule)
        }
    }
}