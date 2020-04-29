package com.onewisebit.scpescape.game.activity.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.NavController
import androidx.navigation.navArgs
import com.onewisebit.scpescape.BaseSCPActivity
import com.onewisebit.scpescape.R
import com.onewisebit.scpescape.databinding.ActivityGameBinding
import com.onewisebit.scpescape.fsm.actions.Action
import com.onewisebit.scpescape.fsm.states.*
import com.onewisebit.scpescape.game.SCPFragmentFactory
import com.onewisebit.scpescape.game.activity.GameContract
import com.onewisebit.scpescape.game.endgame.view.EndGameFragment
import com.onewisebit.scpescape.game.infoturn.view.InfoTurnFragment
import com.onewisebit.scpescape.game.intro.view.IntroFragment
import com.onewisebit.scpescape.game.passdevice.view.PassDeviceFragment
import com.onewisebit.scpescape.game.playerturn.view.PlayerTurnFragment
import com.onewisebit.scpescape.game.roundinfo.view.RoundInfoFragment
import com.onewisebit.scpescape.game.roundresult.view.RoundResultFragment
import com.onewisebit.scpescape.game.vote.view.VoteTurnFragment
import com.onewisebit.scpescape.main.activity.view.MainActivity
import com.onewisebit.scpescape.utilities.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import kotlin.properties.Delegates

class GameActivity : BaseSCPActivity(), GameContract.GameView {

    private val args: GameActivityArgs by navArgs()
    private val presenter: GameContract.GamePresenter by inject { parametersOf(this, args.gameID) }

    private var _binding: ActivityGameBinding? = null
    private val binding get() = _binding!!

    private val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    // not really necessary since we have the callback but nice to learn this
    private var currentState by Delegates.observable<StateGame>(IntroState(), { _, old, new ->
        manageGameState(old, new)
    })

    private val factory: SCPFragmentFactory by inject {
        parametersOf(
            args.gameID,
            fun(action: Action) = actionReceived(action))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //this NEEDS to be called before super.onCreate()
        supportFragmentManager.fragmentFactory = factory

        super.onCreate(savedInstanceState)

        _binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupIntroFragment()

    }

    private fun manageGameState(oldState: StateGame, newState: StateGame) {

        uiScope.launch {
            presenter.saveGameState(oldState, newState)
        }

        /*
        * if necessary we can check the previous state
         when (oldState) {
            is IntroState -> Log.d(TAG, "Play clicked from Intro GameState")
            is RoundInfoState -> Log.d(TAG, "Start round clicked from RoundInfo GameState")
            is PassDeviceState -> Log.d(TAG, "Device passed clicked from PassDevice GameState")
            is PlayerTurnState -> Log.d(TAG, "Play turn clicked from PlayerTurn GameState")
            is PlayerPowerState -> Log.d(TAG, "Play turn clicked from PlayerPower GameState")
            is ShowResultsState -> Log.d(TAG, "Play turn clicked from ShowResults GameState")
        }
         */

        //TODO: check what can be moved to presenter/view
        when (newState) {
            is RoundInfoState -> setupRoundInfoFragment()
            is PassDeviceState -> setupPassDeviceFragment()
            is PlayerTurnState -> uiScope.launch { presenter.setupPlayerTurnFragment() }
            is PlayerPowerState -> uiScope.launch { presenter.setupPlayerPowerFragment() }
            is ShowResultsState -> uiScope.launch { presenter.setupRoundResultsFragment() }
            is CheckVictoryState -> uiScope.launch { presenter.checkVictory() }
            is EndGameState -> Log.d(TAG, "Reached End Game State")
            is CloseGameState -> goToMainActivity()
            else -> throw IllegalStateException("The FSM has reached an unknown state: $newState from state: $oldState")
        }

    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun actionReceived(action: Action) {
        currentState = currentState.consumeAction(action)
    }

    private fun setupIntroFragment() {
        supportFragmentManager.commit {
            replace<IntroFragment>(R.id.fragment_container_view)
        }
    }

    private fun setupRoundInfoFragment() {
        uiScope.launch {
            presenter.addRound()
            supportFragmentManager.commit {
                replace<RoundInfoFragment>(R.id.fragment_container_view)
            }
        }
    }

    private fun setupPassDeviceFragment() {
        uiScope.launch {
            val playerName: String = presenter.newPlayerTurn()
            val arguments = Bundle()
            arguments.putString(ARG_PLAYER_NAME, playerName)
            supportFragmentManager.commit {
                replace<PassDeviceFragment>(R.id.fragment_container_view, args = arguments)
            }
        }
    }

    override fun showPlayerTurnFragment(name: String, role: String, description: String) {
        uiScope.launch {
            val arguments = Bundle()
            arguments.putString(ARG_PLAYER_NAME, name)
            arguments.putString(ARG_ROLE_NAME, role)
            arguments.putString(ARG_ROLE_DESCRIPTION, description)
            // this will run with the last created turn
            supportFragmentManager.commit {
                replace<PlayerTurnFragment>(R.id.fragment_container_view, args = arguments)
            }
        }
    }

    override fun showPlayerVoteFragment(round: String, role: String, isLastTurn: Boolean) {
        val arguments = Bundle()
        arguments.putString(ARG_ROUND_CODE, round)
        arguments.putString(ARG_ROLE_NAME, role)
        arguments.putBoolean(ARG_LAST_TURN, isLastTurn)
        supportFragmentManager.commit {
            replace<VoteTurnFragment>(R.id.fragment_container_view, args = arguments)
        }
    }

    override fun showPlayerInfoFragment(title: String, description: String, isLastTurn: Boolean) {
        val arguments = Bundle()
        arguments.putString(ARG_ACTION_INFO_TITLE, title)
        arguments.putString(ARG_ACTION_INFO_TITLE_DESCRIPTION, description)
        arguments.putBoolean(ARG_LAST_TURN, isLastTurn)
        supportFragmentManager.commit {
            replace<InfoTurnFragment>(R.id.fragment_container_view, args = arguments)
        }
    }

    override fun showRoundResultFragment(roundMessage: List<String>, replayRound: Boolean) {
        val arguments = Bundle()
        //todo: add different types of round info. replayRound -> round_message_type with different options
        if (!replayRound)
            arguments.putStringArray(ARG_KILLED_PLAYERS, roundMessage.toTypedArray())
        else
            arguments.putString(ARG_ROUND_MESSAGE, roundMessage.first())
        supportFragmentManager.commit {
            replace<RoundResultFragment>(R.id.fragment_container_view, args = arguments)
        }
    }

    override fun nextRound() {
        actionReceived(Action.StartNextRoundClicked())
    }

    override fun endGame(winner: String, message: String) {
        // skip the state machine, the game is finished anyway
        actionReceived(Action.VictoryReached())
        val arguments = Bundle()
        arguments.putString(ARG_GAME_WINNER, winner)
        arguments.putString(ARG_WINNING_MESSAGE, message)
        supportFragmentManager.commit {
            replace<EndGameFragment>(R.id.fragment_container_view, args = arguments)
        }
    }

    // using fragmentFactory to manage navigation for now
    private fun setupNavigation(navController: NavController) {
        // setting this here since it's the starting activity of a new graph
        // see https://developer.android.com/guide/navigation/navigation-migrate#pass_activity_destination_args_to_a_start_destination_fragment
        navController.setGraph(R.navigation.nav_game, args.toBundle())
    }

    override fun onDestroy() {
        presenter.onDestroy()
        _binding = null
        super.onDestroy()
    }


    companion object {
        private val TAG = GameActivity::class.java.simpleName
    }

}
