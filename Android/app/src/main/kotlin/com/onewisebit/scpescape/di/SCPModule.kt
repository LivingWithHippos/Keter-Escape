package com.onewisebit.scpescape.di

import android.content.Context
import android.content.SharedPreferences
import com.onewisebit.scpescape.fsm.actions.Action
import com.onewisebit.scpescape.game.GameContract
import com.onewisebit.scpescape.game.IntroContract
import com.onewisebit.scpescape.game.RoundInfoContract
import com.onewisebit.scpescape.game.basemvp.*
import com.onewisebit.scpescape.game.model.GameModelImpl
import com.onewisebit.scpescape.game.model.IntroModelImpl
import com.onewisebit.scpescape.game.model.RoundInfoModelImpl
import com.onewisebit.scpescape.game.presenter.GamePresenterImpl
import com.onewisebit.scpescape.game.presenter.IntroPresenterImpl
import com.onewisebit.scpescape.game.presenter.RoundInfoPresenterImpl
import com.onewisebit.scpescape.game.view.SCPFragmentFactory
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
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val appModule = module {

    // Shared Preferences

    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            PREF_FILE,
            Context.MODE_PRIVATE
        )
    }

    // DAOs

    single { SCPDatabase.getInstance(get()) }
    single { get<SCPDatabase>().playerDAO() }
    single { get<SCPDatabase>().roleDAO() }
    single { get<SCPDatabase>().gameDAO() }
    single { get<SCPDatabase>().modeDAO() }
    single { get<SCPDatabase>().participantDAO() }
    single { get<SCPDatabase>().roundDAO() }
    single { get<SCPDatabase>().turnDAO() }

    // Repositories

    single<InGameRepository> { GameRepository(get()) }
    single<InPlayerRepository> { PlayerRepository(get()) }
    single<InParticipantRepository> { ParticipantRepository(get()) }
    single<InRoundRepository> { RoundRepository(get(),get(),get()) }
    single<InTurnRepository> { TurnRepository(get()) }
    // TODO: remove when/if Mode is removed
    single { ModeRepository(get()) }
    single<InModeJSONRepository> { ModeJSONRepository(get()) }

    // Composable Game MVP, see ContractGame.kt

    factory<ContractGame.ModelGame> {
        ModelGameImpl(get())
    }

    factory<ContractGame.PresenterGame> { (game: Long) ->
        PresenterGameImpl(get(), game)
    }

    factory<ContractParticipant.ModelParticipant> {
        ModelParticipantImpl(get())
    }

    factory<ContractParticipant.PresenterParticipant> { (game: Long) ->
        PresenterParticipantImpl(get(), game)
    }

    factory<ContractPlayer.ModelPlayer> {
        ModelPlayerImpl(get())
    }

    factory<ContractPlayer.PresenterPlayer> { (game: Long) ->
        PresenterPlayerImpl(get(), game)
    }

    factory<ContractRound.ModelRound> {
        ModelRoundImpl(get())
    }

    factory<ContractRound.PresenterRound> { (game: Long) ->
        PresenterRoundImpl(get(), game)
    }

    factory<ContractTurn.ModelTurn> {
        ModelTurnImpl(get())
    }

    factory<ContractTurn.PresenterTurn> { (game: Long) ->
        PresenterTurnImpl(get(), get(), game)
    }

    factory<ContractMode.ModelMode> {
        ModelModeImpl(get(), get())
    }

    factory<ContractMode.PresenterMode> { (game: Long) ->
        PresenterModeImpl(get(), game)
    }

    // MainActivity MVP
    factory<StartContract.StartModel> {
        StartActivityModel(
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

    // NewGameSettings Fragment
    factory<GameSettingsContract.GameSettingsModel> {
        GameSettingsModelImpl(
            get(),
            get()
        )
    }


    factory<GameSettingsContract.GameSettingsPresenter> { (view: GameSettingsContract.GameSettingsView) ->
        GameSettingsPresenterImpl(
            view,
            get()
        )
    }

    // Players List MVP

    factory<PlayersContract.PlayersModel> {
        PlayersModelImpl(
            get(),
            get(),
            get()
        )
    }


    factory<PlayersContract.PlayersPresenter> { (view: PlayersContract.PlayersView) ->
        PlayersPresenterImpl(
            view,
            get()
        )
    }

    // Game modes list MVP

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

    //TODO: check if this needs to be a factory or a single
    //single<SCPFragmentFactory> { (game: Long, state: StateGame) -> SCPFragmentFactory(game, state)}
    factory<SCPFragmentFactory> { (game: Long, onActionListener: (action: Action) -> Unit) ->
        SCPFragmentFactory(
            game,
            onActionListener
        )
    }

    // GameActivity MVP

    factory<GameContract.GameModel> {
        GameModelImpl(
            get(),
            get(),
            get(),
            get()
        )
    }

    factory<GameContract.GamePresenter> { (view: GameContract.GameView, game: Long) ->
        GamePresenterImpl(
            view,
            get(),
            get { parametersOf(game) },
            get { parametersOf(game) },
            get { parametersOf(game) },
            get { parametersOf(game) },
            game
        )
    }

    // IntroFragment MVP

    factory<IntroContract.IntroModel> {
        IntroModelImpl(
            get(),
            get(),
            get()
        )
    }

    factory<IntroContract.IntroPresenter> { (view: IntroContract.IntroView, game: Long) ->
        IntroPresenterImpl(
            view,
            get(),
            get { parametersOf(game) },
            get { parametersOf(game) },
            game
        )
    }

    // RoundInfo Fragment MVP


    factory<RoundInfoContract.RoundInfoModel> {
        RoundInfoModelImpl(
            get(),
            get()
        )
    }

    factory<RoundInfoContract.RoundInfoPresenter> { (view: RoundInfoContract.RoundInfoView, game: Long) ->
        RoundInfoPresenterImpl(
            view,
            get(),
            get { parametersOf(game) },
            game
        )
    }

}