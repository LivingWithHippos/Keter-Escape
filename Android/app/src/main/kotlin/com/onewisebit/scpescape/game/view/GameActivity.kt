package com.onewisebit.scpescape.game.view

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.navArgs
import com.onewisebit.scpescape.BaseSCPActivity
import com.onewisebit.scpescape.R
import com.onewisebit.scpescape.databinding.ActivityGameBinding
import com.onewisebit.scpescape.game.GameStateContract
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class GameActivity : BaseSCPActivity(), GameStateContract.GameStateView {

    private lateinit var binding: ActivityGameBinding
    private val navController by lazy { findNavController(R.id.nav_host) }
    private val args: GameActivityArgs by navArgs()
    private val presenter: GameStateContract.GameStatePresenter by inject { parametersOf(this,args.gameID) }

    //private val machine: GameMachine = GameMachine(presenter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // setting this here since it's the starting activity of a new graph
        // see https://developer.android.com/guide/navigation/navigation-migrate#pass_activity_destination_args_to_a_start_destination_fragment
        navController.setGraph(R.navigation.nav_game, args.toBundle())

        //presenter.assignRoles()
    }
}
