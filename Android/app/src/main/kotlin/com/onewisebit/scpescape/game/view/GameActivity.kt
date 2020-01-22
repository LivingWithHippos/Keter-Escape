package com.onewisebit.scpescape.game.view

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.navArgs
import com.onewisebit.scpescape.BaseSCPActivity
import com.onewisebit.scpescape.R
import com.onewisebit.scpescape.databinding.ActivityGameBinding
import com.onewisebit.scpescape.game.GameStateContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class GameActivity : BaseSCPActivity(), GameStateContract.GameStateView {

    private val navController by lazy { findNavController(R.id.nav_host) }
    private val args: GameActivityArgs by navArgs()
    private val presenter: GameStateContract.GameStatePresenter by inject { parametersOf(this,args.gameID) }

    private var _binding: ActivityGameBinding? = null
    private val binding get() = _binding!!

    private val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    //private val machine: GameMachine = GameMachine(presenter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // setting this here since it's the starting activity of a new graph
        // see https://developer.android.com/guide/navigation/navigation-migrate#pass_activity_destination_args_to_a_start_destination_fragment
        navController.setGraph(R.navigation.nav_game, args.toBundle())

        uiScope.launch {
            presenter.assignRoles()
        }
    }

    override fun onDestroy() {
        job.cancel()
        presenter.onDestroy()
        _binding = null
        super.onDestroy()
    }


}
