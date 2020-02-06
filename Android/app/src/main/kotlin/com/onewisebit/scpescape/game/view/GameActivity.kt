package com.onewisebit.scpescape.game.view

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
import com.onewisebit.scpescape.fsm.states.RoundInfoState
import com.onewisebit.scpescape.fsm.states.IntroState
import com.onewisebit.scpescape.fsm.states.PassDeviceState
import com.onewisebit.scpescape.fsm.states.StateGame
import com.onewisebit.scpescape.game.GameContract
import com.onewisebit.scpescape.utilities.ARG_PLAYER_NAME
import com.onewisebit.scpescape.utilities.NIGHT
import com.onewisebit.scpescape.utilities.ARG_TURN_NUMBER
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

        //TODO: remove logs
        when (oldState) {
            is IntroState -> Log.d(TAG, "Play clicked from Intro GameState")
            is RoundInfoState -> Log.d(TAG, "Start round clicked from RoundInfo GameState")
        }

        when (newState) {
            is RoundInfoState -> setupRoundInfoFragment(newState.dayOrNight)
            is PassDeviceState -> setupPassDeviceFragment()
        }

    }

    private fun actionReceived(action: Action) {
        currentState = currentState.consumeAction(action)
    }

    private fun setupIntroFragment() {
        supportFragmentManager.commit {
            replace<IntroFragment>(R.id.fragment_container_view)
        }
    }

    private fun setupRoundInfoFragment(dayOrNight: Int) {
        uiScope.launch {
            //TODO: replace with round state managing
            val roundCode = if (dayOrNight == NIGHT) "lights_out" else "lights_on"
            presenter.addRound(roundCode)
            supportFragmentManager.commit {
                replace<RoundInfoFragment>(R.id.fragment_container_view)
            }
        }
    }

    private fun setupPassDeviceFragment() {
        uiScope.launch {
            val playerName: String = presenter.newPlayerTurn()
            val arguments = Bundle()
            arguments.putString(ARG_PLAYER_NAME,playerName)
            supportFragmentManager.commit {
                replace<PassDeviceFragment>(R.id.fragment_container_view, args = arguments)
            }
        }
    }

    private fun setupNavigation(navController: NavController){
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
