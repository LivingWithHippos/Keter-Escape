package com.onewisebit.scpescape.game.passdevice.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.onewisebit.scpescape.R
import com.onewisebit.scpescape.databinding.FragmentPassDeviceBinding
import com.onewisebit.scpescape.fsm.actions.Action
import com.onewisebit.scpescape.BaseSCPFragment
import com.onewisebit.scpescape.game.BaseGameFragment
import com.onewisebit.scpescape.utilities.ARG_PLAYER_NAME

/**
 * A simple [Fragment] subclass.
 */
class PassDeviceFragment(gameID: Long, private val onActionListener: (action: Action) -> Unit) :
    BaseGameFragment<FragmentPassDeviceBinding>(gameID, onActionListener) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPassDeviceBinding.inflate(layoutInflater)
        val playerName: String = arguments?.getString(ARG_PLAYER_NAME)
            ?: throw IllegalArgumentException("Couldn't load player name from arguments")
        initView(playerName)
        return binding.root
    }

    fun initView(playerName: String) {
        //TODO: add player picture loading
        //TODO: add a spinning waiting message
        binding.tvPassDevice.text = getString(R.string.pass_to_player, playerName)
        binding.bConfirmPlayer.text = getString(R.string.i_am_player, playerName)
        binding.bConfirmPlayer.setOnClickListener {
            binding.bConfirmPlayer.isEnabled = false
            onActionListener(Action.PassedToPlayerClicked())
        }
    }
}
