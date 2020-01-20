package com.onewisebit.scpescape.newgamesettings.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.onewisebit.scpescape.R
import com.onewisebit.scpescape.databinding.FragmentNewGameSettingsBinding
import com.onewisebit.scpescape.model.ModeDataClass
import com.onewisebit.scpescape.newgamesettings.GameSettingsContract
import com.onewisebit.scpescape.utilities.GAME_CLASSIC_MAX_PLAYERS
import com.onewisebit.scpescape.utilities.GAME_CLASSIC_MID_PLAYERS
import com.onewisebit.scpescape.utilities.GAME_CLASSIC_MIN_PLAYERS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class NewGameSettingsFragment : Fragment(), GameSettingsContract.GameSettingsView {

    private lateinit var binding: FragmentNewGameSettingsBinding
    private val presenter: GameSettingsContract.GameSettingsPresenter by inject { parametersOf(this) }
    private val args: NewGameSettingsFragmentArgs by navArgs()


    private val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewGameSettingsBinding.inflate(layoutInflater)

        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uiScope.launch {
            val mode = presenter.getMode(args.gameMode)
            if (mode != null)
                setupView(view, mode)
            else
                Log.d(TAG, "Error retrieveing mode: ${args.gameMode}")
        }

    }


    private fun setupView(view: View, mode: ModeDataClass) {
        //TODO: load values from mode
        binding.tvRoles.setText(R.string.mode_classic_players_lower)
        binding.npPlayerPicker.minValue = mode.min
        binding.npPlayerPicker.maxValue = mode.max

        // change description string according to players number
        binding.npPlayerPicker.setOnValueChangedListener { _, _, newVal ->
            when (newVal) {
                in GAME_CLASSIC_MIN_PLAYERS..GAME_CLASSIC_MID_PLAYERS -> binding.tvRoles.setText(R.string.mode_classic_players_lower)
                in GAME_CLASSIC_MID_PLAYERS + 1..GAME_CLASSIC_MAX_PLAYERS -> binding.tvRoles.setText(
                    R.string.mode_classic_players_higher
                )
                //TODO: make this clickable to report feedback
                else -> binding.tvRoles.setText(R.string.players_count_error)
            }
        }

        // observe if a new game is created and move to the next settings page
        presenter.onNewGame().observe(this, Observer<Long> { gameID ->
            val action =
                NewGameSettingsFragmentDirections.actionNewGameSettingsToParticipantsChoice(
                    binding.npPlayerPicker.value,
                    gameID
                )
            view.findNavController().navigate(action)
        })

        // create a new game in the db
        binding.fabChoosePlayers.setOnClickListener {
            //TODO: check if there's a better way to do this without using two presenter methods (createNewGame, onNewGame)
            presenter.createNewGame(args.gameMode, args.gameType)
        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    companion object {
        private val TAG = NewGameSettingsFragment::class.java.simpleName
    }

}