package com.onewisebit.scpescape.main.newgamesettings.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.onewisebit.scpescape.BaseSCPFragment
import com.onewisebit.scpescape.R
import com.onewisebit.scpescape.databinding.FragmentNewGameSettingsBinding
import com.onewisebit.scpescape.main.newgamesettings.GameSettingsContract
import com.onewisebit.scpescape.model.parsed.ModeDataClass
import kotlinx.android.synthetic.main.fragment_new_game_settings.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class NewGameSettingsFragment : BaseSCPFragment<FragmentNewGameSettingsBinding>(),
    GameSettingsContract.GameSettingsView {

    private val presenter: GameSettingsContract.GameSettingsPresenter by inject { parametersOf(this) }
    private val args: NewGameSettingsFragmentArgs by navArgs()


    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewGameSettingsBinding.inflate(layoutInflater)

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
                Log.d(TAG, "Error retrieving mode: ${args.gameMode}")
        }

    }


    private fun setupView(view: View, mode: ModeDataClass) {
        binding.tvRoles.setText(R.string.mode_classic_players_lower)
        binding.npPlayerPicker.minValue = mode.min
        binding.npPlayerPicker.maxValue = mode.max
        updateRolesDivision(mode, npPlayerPicker.value)
        // change description string according to players number
        binding.npPlayerPicker.setOnValueChangedListener { _, _, newVal ->
            updateRolesDivision(mode, newVal)
        }

        // observe if a new game is created and move to the next settings page
        presenter.onNewGame().observe(viewLifecycleOwner, Observer<Long> { gameID ->
            val action =
                NewGameSettingsFragmentDirections.actionNewGameSettingsToParticipantsChoice(
                    binding.npPlayerPicker.value,
                    gameID
                )
            view.findNavController().navigate(action)
        })

        // create a new game in the db
        binding.fabChoosePlayers.setOnClickListener {
            binding.fabChoosePlayers.isEnabled = false
            //TODO: check if there's a better way to do this without using two presenter methods (createNewGame, onNewGame)
            presenter.createNewGame(args.gameMode, args.gameType)
        }
    }

    //TODO: move this to the presenter
    private fun updateRolesDivision(mode: ModeDataClass, newVal: Int) {
        mode.rolesDivision.forEach {
            if ((newVal >= it.minPlayers) && (newVal <= it.maxPlayers)) {
                val builder = StringBuilder()
                var lastLine = ""
                it.roles.forEach { rolediv ->
                    if (rolediv.default)
                        lastLine = getString(R.string.remaining_players, rolediv.role)
                    else
                        builder.append(rolediv.quantity).append(" ").append(rolediv.role)
                            .append("\n")
                }
                builder.append(lastLine)
                binding.tvRoles.text = builder.toString()
            }
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