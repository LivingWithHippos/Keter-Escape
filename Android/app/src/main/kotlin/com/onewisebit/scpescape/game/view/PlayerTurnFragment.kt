package com.onewisebit.scpescape.game.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.onewisebit.scpescape.R
import com.onewisebit.scpescape.databinding.FragmentPlayerTurnBinding
import com.onewisebit.scpescape.fsm.actions.Action

/**
 * A simple [Fragment] subclass.
 */
class PlayerTurnFragment (gameID: Long, private val onActionListener: (action: Action) -> Unit) :
BaseGameFragment(gameID, onActionListener) {

    private var _binding : FragmentPlayerTurnBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlayerTurnBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
