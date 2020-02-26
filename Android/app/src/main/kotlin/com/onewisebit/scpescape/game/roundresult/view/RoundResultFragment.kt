package com.onewisebit.scpescape.game.roundresult.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.onewisebit.scpescape.databinding.FragmentRoundResultBinding
import com.onewisebit.scpescape.fsm.actions.Action
import com.onewisebit.scpescape.BaseSCPFragment
import com.onewisebit.scpescape.game.BaseGameFragment

/**
 * A simple [Fragment] subclass.
 */
class RoundResultFragment(gameID: Long, private val onActionListener: (action: Action) -> Unit) :
    BaseGameFragment<FragmentRoundResultBinding>(gameID, onActionListener) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRoundResultBinding.inflate(layoutInflater)
        return binding.root
    }

}
