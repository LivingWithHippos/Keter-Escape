package com.onewisebit.scpescape.di

import android.content.Context
import android.content.SharedPreferences
import com.onewisebit.scpescape.fsm.GameStateContract
import com.onewisebit.scpescape.fsm.GameStateModelImpl
import com.onewisebit.scpescape.fsm.GameStatePresenterImpl
import com.onewisebit.scpescape.game.IntroContract
import com.onewisebit.scpescape.game.model.IntroModelImpl
import com.onewisebit.scpescape.game.presenter.IntroPresenterImpl
import com.onewisebit.scpescape.main.StartContract
import com.onewisebit.scpescape.main.model.StartActivityModel
import com.onewisebit.scpescape.main.presenter.StartActivityPresenterImpl
import com.onewisebit.scpescape.model.database.SCPDatabase
import com.onewisebit.scpescape.model.repositories.GameRepository
import com.onewisebit.scpescape.model.repositories.ModeRepository
import com.onewisebit.scpescape.model.repositories.ParticipantRepository
import com.onewisebit.scpescape.model.repositories.PlayerRepository
import com.onewisebit.scpescape.newgamesettings.GameSettingsContract
import com.onewisebit.scpescape.newgamesettings.model.GameSettingsModelImpl
import com.onewisebit.scpescape.newgamesettings.presenter.GameSettingsPresenterImpl
import com.onewisebit.scpescape.playerslist.PlayersContract
import com.onewisebit.scpescape.playerslist.model.PlayersModelImpl
import com.onewisebit.scpescape.playerslist.presenter.PlayersPresenterImpl
import com.onewisebit.scpescape.utilities.PREF_FILE
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
    single { ParticipantRepository(get()) }
    single { ModeRepository(get()) }

    factory<StartContract.StartModel> {
        StartActivityModel(
            get(),
            get()
        )
    }
    factory<GameSettingsContract.GameSettingsModel> {
        GameSettingsModelImpl(
            get(),
            get()
        )
    }
    factory<PlayersContract.PlayersModel> {
        PlayersModelImpl(
            get(),
            get(),
            get()
        )
    }
    factory<IntroContract.IntroModel> {
        IntroModelImpl(
            get()
        )
    }
    factory<StartContract.StartPresenter> { (view: StartContract.StartView) ->
        StartActivityPresenterImpl(
            view,
            get()
        )
    }
    factory<PlayersContract.PlayersPresenter> { (view: PlayersContract.PlayersView) ->
        PlayersPresenterImpl(
            view,
            get()
        )
    }
    factory<GameSettingsContract.GameSettingsPresenter> { (view: GameSettingsContract.GameSettingsView) ->
        GameSettingsPresenterImpl(
            view,
            get()
        )
    }

    factory<IntroContract.IntroPresenter> { (view: IntroContract.IntroView) -> IntroPresenterImpl(view,get()) }

    factory<GameStateContract.GameStateModel> {
        GameStateModelImpl(
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }

    factory<GameStateContract.GameStatePresenter> { (view: GameStateContract.GameStateView, game: Long) -> GameStatePresenterImpl(view,get(), game) }

}