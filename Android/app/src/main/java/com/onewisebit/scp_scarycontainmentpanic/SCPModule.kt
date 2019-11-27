package com.onewisebit.scp_scarycontainmentpanic

import android.content.Context
import android.content.SharedPreferences
import com.onewisebit.scp_scarycontainmentpanic.model.GameRepository
import com.onewisebit.scp_scarycontainmentpanic.model.InGameRepository
import com.onewisebit.scp_scarycontainmentpanic.utilities.PREF_FILE
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single<SharedPreferences> { androidContext().getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)}
}