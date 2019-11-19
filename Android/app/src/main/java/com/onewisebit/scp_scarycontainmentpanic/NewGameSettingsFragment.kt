package com.onewisebit.scp_scarycontainmentpanic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.onewisebit.scp_scarycontainmentpanic.databinding.FragmentNewGameSettingsBinding
import com.onewisebit.scp_scarycontainmentpanic.utilities.GAME_CLASSIC_MAX_PLAYERS
import com.onewisebit.scp_scarycontainmentpanic.utilities.GAME_CLASSIC_MID_PLAYERS
import com.onewisebit.scp_scarycontainmentpanic.utilities.GAME_CLASSIC_MIN_PLAYERS

class NewGameSettingsFragment : Fragment() {

    private lateinit var binding: FragmentNewGameSettingsBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //TODO: change settings according to chosen game mode
        val args: NewGameSettingsFragmentArgs by navArgs()
        binding = FragmentNewGameSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvRoles.setText(R.string.mode_classic_players_lower)
        binding.npPlayerPicker.minValue = GAME_CLASSIC_MIN_PLAYERS
        binding.npPlayerPicker.maxValue = GAME_CLASSIC_MAX_PLAYERS
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
        binding.fabChoosePlayers.setOnClickListener{
            val action = NewGameSettingsFragmentDirections.actionNewGameSettingsToPlayersChoice()
            view.findNavController().navigate(action)
        }
    }
}