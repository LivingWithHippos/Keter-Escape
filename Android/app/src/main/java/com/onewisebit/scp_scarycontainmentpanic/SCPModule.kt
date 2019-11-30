package com.onewisebit.scp_scarycontainmentpanic

import android.content.Context
import android.content.SharedPreferences
import com.onewisebit.scp_scarycontainmentpanic.model.*
import com.onewisebit.scp_scarycontainmentpanic.presenters.GameSettingsPresenterImpl
import com.onewisebit.scp_scarycontainmentpanic.presenters.PlayersPresenterImpl
import com.onewisebit.scp_scarycontainmentpanic.presenters.StartActivityPresenterImpl
import com.onewisebit.scp_scarycontainmentpanic.utilities.PREF_FILE
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            PREF_FILE,
            Context.MODE_PRIVATE
        )
    }

    single { SCPDatabase.getInstance(get()) }
    single { get<SCPDatabase>().playerDAO() }
    single { get<SCPDatabase>().roleDAO() }
    single { get<SCPDatabase>().gameDAO() }
    single { get<SCPDatabase>().modeDAO() }
    single { get<SCPDatabase>().participantDAO() }

    single { GameRepository(get()) }
    single { PlayerRepository(get()) }

    factory<StartContract.StartModel> { StartActivityModel(get(),get()) }
    factory { GameSettingsModelImpl(get()) }
    factory { PlayersModelImpl(get()) }
    factory<StartContract.StartPresenter> { (view: StartContract.StartView) -> StartActivityPresenterImpl(view,get()) }
    factory<PlayersContract.PlayersPresenter> { (view: PlayersContract.PlayersView, model: PlayersContract.PlayersModel) -> PlayersPresenterImpl(view,model) }
    factory<GameSettingsContract.GameSettingsPresenter> { (view: GameSettingsContract.GameSettingsView, model: GameSettingsContract.GameSettingsModel) -> GameSettingsPresenterImpl(view,model) }
}