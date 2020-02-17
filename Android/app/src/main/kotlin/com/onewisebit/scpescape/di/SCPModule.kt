package com.onewisebit.scpescape.di

import android.content.Context
import android.content.SharedPreferences
import com.onewisebit.scpescape.fsm.actions.Action
import com.onewisebit.scpescape.game.SCPFragmentFactory
import com.onewisebit.scpescape.game.activity.GameContract
import com.onewisebit.scpescape.game.activity.model.GameModelImpl
import com.onewisebit.scpescape.game.activity.presenter.GamePresenterImpl
import com.onewisebit.scpescape.game.composable.*
import com.onewisebit.scpescape.game.composable.model.*
import com.onewisebit.scpescape.game.composable.presenter.*
import com.onewisebit.scpescape.game.intro.IntroContract
import com.onewisebit.scpescape.game.intro.model.IntroModelImpl
import com.onewisebit.scpescape.game.intro.presenter.IntroPresenterImpl
import com.onewisebit.scpescape.game.playerturn.PlayerTurnContract
import com.onewisebit.scpescape.game.playerturn.model.PlayerTurnModelImpl
import com.onewisebit.scpescape.game.playerturn.presenter.PlayerTurnPresenterImpl
import com.onewisebit.scpescape.game.roundinfo.RoundInfoContract
import com.onewisebit.scpescape.game.roundinfo.model.RoundInfoModelImpl
import com.onewisebit.scpescape.game.roundinfo.presenter.RoundInfoPresenterImpl
import com.onewisebit.scpescape.game.vote.VoteContract
import com.onewisebit.scpescape.game.vote.model.VoteModelImpl
import com.onewisebit.scpescape.game.vote.presenter.VotePresenterImpl
import com.onewisebit.scpescape.main.activity.StartContract
import com.onewisebit.scpescape.main.activity.model.StartActivityModel
import com.onewisebit.scpescape.main.activity.presenter.StartActivityPresenterImpl
import com.onewisebit.scpescape.main.modesList.GameModesContract
import com.onewisebit.scpescape.main.modesList.model.GameModesModelImpl
import com.onewisebit.scpescape.main.modesList.presenter.GameModesPresenterImpl
import com.onewisebit.scpescape.main.newgamesettings.GameSettingsContract
import com.onewisebit.scpescape.main.newgamesettings.model.GameSettingsModelImpl
import com.onewisebit.scpescape.main.newgamesettings.presenter.GameSettingsPresenterImpl
import com.onewisebit.scpescape.main.playerslist.PlayersContract
import com.onewisebit.scpescape.main.playerslist.model.PlayersModelImpl
import com.onewisebit.scpescape.main.playerslist.presenter.PlayersPresenterImpl
import com.onewisebit.scpescape.model.database.SCPDatabase
import com.onewisebit.scpescape.model.repositories.*
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
    single { get<SCPDatabase>().voteDAO() }

    // Repositories

    single<InGameRepository> { GameRepository(get()) }
    single<InPlayerRepository> { PlayerRepository(get()) }
    single<InParticipantRepository> { ParticipantRepository(get()) }
    single<InRoundRepository> { RoundRepository(get(), get(), get()) }
    single<InTurnRepository> { TurnRepository(get()) }
    // TODO: remove when/if Mode is removed
    single { ModeRepository(get()) }
    single<InModeJSONRepository> { ModeJSONRepository(get()) }
    single<InTurnActionRepository> { TurnActionRepository(get(), get()) }
    single<InVoteRepository> { VoteRepository(get(), get()) }

    // Composable Game MVP, see ContractGame.kt

    factory<ContractGame.ModelGame> {
        ModelGameImpl(get())
    }

    factory<ContractGame.PresenterGame> { (game: Long) ->
        PresenterGameImpl(
            get(),
            game
        )
    }

    factory<ContractParticipant.ModelParticipant> {
        ModelParticipantImpl(get())
    }

    factory<ContractParticipant.PresenterParticipant> { (game: Long) ->
        PresenterParticipantImpl(
            get(),
            game
        )
    }

    factory<ContractPlayer.ModelPlayer> {
        ModelPlayerImpl(get())
    }

    factory<ContractPlayer.PresenterPlayer> { (game: Long) ->
        PresenterPlayerImpl(
            get(),
            game
        )
    }

    factory<ContractRound.ModelRound> {
        ModelRoundImpl(get())
    }

    factory<ContractRound.PresenterRound> { (game: Long) ->
        PresenterRoundImpl(
            get(),
            game
        )
    }

    factory<ContractTurn.ModelTurn> {
        ModelTurnImpl(get())
    }

    factory<ContractTurn.PresenterTurn> { (game: Long) ->
        PresenterTurnImpl(
            get(),
            get(),
            game
        )
    }

    factory<ContractMode.ModelMode> {
        ModelModeImpl(get(), get())
    }

    factory<ContractMode.PresenterMode> { (game: Long) ->
        PresenterModeImpl(
            get(),
            game
        )
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

    // Turn Actions MVP

    factory<ContractAction.ModelAction> {
        ModelActionImpl(
            get()
        )
    }

    factory<ContractAction.PresenterAction> { (gameId: Long) ->
        PresenterActionImpl(
            get(),
            gameId
        )
    }

    // Vote MVP

    factory<ContractVote.ModelVote> {
        ModelVoteImpl(
            get()
        )
    }

    factory<ContractVote.PresenterVote> { (gameId: Long) ->
        PresenterVoteImpl(
            get(),
            gameId
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
            get { parametersOf(game) },
            get { parametersOf(game) },
            game
        )
    }

    // IntroFragment MVP

    factory<IntroContract.IntroModel> {
        IntroModelImpl(
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

    // Player Turn Fragment MVP

    factory<PlayerTurnContract.PlayerTurnModel> {
        PlayerTurnModelImpl(
            get(),
            get()
        )
    }

    factory<PlayerTurnContract.PlayerTurnPresenter> { (view: PlayerTurnContract.PlayerTurnView, game: Long) ->
        PlayerTurnPresenterImpl(
            view,
            get(),
            get { parametersOf(game) },
            get { parametersOf(game) },
            game
        )
    }

    // Player Vote Power Fragment MVP

    factory<VoteContract.VoteModel> {
        VoteModelImpl(
            get(),
            get(),
            get(),
            get()
        )
    }

    factory<VoteContract.VotePresenter> { (view: VoteContract.VoteView, game: Long) ->
        VotePresenterImpl(
            view,
            get { parametersOf(game) },
            get { parametersOf(game) },
            get { parametersOf(game) },
            get { parametersOf(game) },
            game
        )
    }

}