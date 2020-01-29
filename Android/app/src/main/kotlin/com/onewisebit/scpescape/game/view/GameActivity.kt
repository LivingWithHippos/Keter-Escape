package com.onewisebit.scpescape.game.view

import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.navArgs
import com.onewisebit.scpescape.BaseSCPActivity
import com.onewisebit.scpescape.R
import com.onewisebit.scpescape.databinding.ActivityGameBinding
import com.onewisebit.scpescape.fsm.actions.Action
import com.onewisebit.scpescape.fsm.states.DayNightState
import com.onewisebit.scpescape.fsm.states.IntroState
import com.onewisebit.scpescape.fsm.states.StateGame
import com.onewisebit.scpescape.game.GameStateContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import kotlin.properties.Delegates

class GameActivity : BaseSCPActivity(), GameStateContract.GameStateView {

    private val args: GameActivityArgs by navArgs()
    private val presenter: GameStateContract.GameStatePresenter by inject { parametersOf(this,args.gameID) }

    private var _binding: ActivityGameBinding? = null
    private val binding get() = _binding!!

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    // not really necessary since we have the callback but nice to learn this
    var currentState by Delegates.observable<StateGame>(IntroState(), { _, old, new ->
        manageGameState(old, new)
    })

    private val factory: SCPFragmentFactory by inject { parametersOf(args.gameID, fun(action : Action) = actionReceived(action)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        //this NEEDS to be called before the super.onCreate()
        supportFragmentManager.fragmentFactory = factory

        super.onCreate(savedInstanceState)

        _binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = supportFragmentManager.fragmentFactory.instantiate(classLoader, IntroFragment::class.java.name)
        supportFragmentManager.beginTransaction().replace(R.id.rootLayout, fragment).commitNow()

        //TODO: remove navigation here and manage Fragments with a FragmentManager and the FSM to allow more flexibility

        // setting this here since it's the starting activity of a new graph
        // see https://developer.android.com/guide/navigation/navigation-migrate#pass_activity_destination_args_to_a_start_destination_fragment
        //navController.setGraph(R.navigation.nav_game, args.toBundle())

        uiScope.launch {
            presenter.assignRoles()
        }
    }

    private fun manageGameState(oldState: StateGame, newState: StateGame) {

        when (oldState) {
            is IntroState -> Log.d(TAG,"Play clicked from Intro GameState")
        }

        when (newState) {
            is DayNightState -> Log.d(TAG,"Preparing day or night GameState")
        }

    }
    private fun actionReceived(action: Action){
        currentState = currentState.consumeAction(action)
    }

    override fun onDestroy() {
        job.cancel()
        presenter.onDestroy()
        _binding = null
        super.onDestroy()
    }


    companion object {
        private val TAG = GameActivity::class.java.simpleName
    }

}
