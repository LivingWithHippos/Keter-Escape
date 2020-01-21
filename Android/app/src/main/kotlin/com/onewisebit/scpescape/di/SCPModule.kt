package com.onewisebit.scpescape.di

import android.content.Context
import android.content.SharedPreferences
import com.onewisebit.scpescape.game.GameStateContract
import com.onewisebit.scpescape.game.model.GameStateModelImpl
import com.onewisebit.scpescape.game.presenter.GameStatePresenterImpl
import com.onewisebit.scpescape.game.IntroContract
import com.onewisebit.scpescape.game.model.IntroModelImpl
import com.onewisebit.scpescape.game.presenter.IntroPresenterImpl
import com.onewisebit.scpescape.main.StartContract
import com.onewisebit.scpescape.main.model.StartActivityModel
import com.onewisebit.scpescape.main.presenter.StartActivityPresenterImpl
import com.onewisebit.scpescape.model.database.SCPDatabase
import com.onewisebit.scpescape.model.repositories.*
import com.onewisebit.scpescape.modesList.GameModesContract
import com.onewisebit.scpescape.modesList.model.GameModesModelImpl
import com.onewisebit.scpescape.modesList.presenter.GameModesPresenterImpl
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
    single { get<SCPDatabase>().roundDAO() }
    single { get<SCPDatabase>().turnDAO() }

    single<InGameRepository> { GameRepository(get()) }
    single<InPlayerRepository> { PlayerRepository(get()) }
    single<InParticipantRepository> { ParticipantRepository(get()) }
    single<InRoundRepository> { RoundRepository(get()) }
    single<InTurnRepository> { TurnRepository(get()) }
    single { ModeRepository(get()) }
    // TODO: replace other single with single<interface> {implementation}
    single<InModelNewRepository> { ModeNewRepository(get()) }

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
            get(),
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

    factory<IntroContract.IntroPresenter> { (view: IntroContract.IntroView) ->
        IntroPresenterImpl(
            view,
            get()
        )
    }

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

    factory<GameStateContract.GameStatePresenter> { (view: GameStateContract.GameStateView, game: Long) ->
        GameStatePresenterImpl(
            view,
            get(),
            game
        )
    }


    factory<GameModesContract.GameModesModel> {
        GameModesModelImpl(
            get()
        )
    }

    factory<GameModesContract.GameModesPresenter> { (view: GameModesContract.GameModesView) ->
        GameModesPresenterImpl(
            view,
            get()
        )
    }


}