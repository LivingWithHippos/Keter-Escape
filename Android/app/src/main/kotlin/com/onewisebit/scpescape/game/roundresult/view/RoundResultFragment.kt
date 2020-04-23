package com.onewisebit.scpescape.game.roundresult.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.onewisebit.scpescape.databinding.FragmentRoundResultBinding
import com.onewisebit.scpescape.fsm.actions.Action
import com.onewisebit.scpescape.BaseSCPFragment
import com.onewisebit.scpescape.R
import com.onewisebit.scpescape.game.BaseGameFragment
import com.onewisebit.scpescape.utilities.ARG_KILLED_PLAYERS

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
           val killedPlayers = it.getStringArray(ARG_KILLED_PLAYERS)!!
            if (killedPlayers.isNotEmpty())
                setupWithDeaths(killedPlayers)
            else
                setupNoDeaths()
        }

        binding.fabNextStep.setOnClickListener {
            onActionListener(Action.ResultSeenClicked())
        }
    }

    // todo: add contract to this fragment
    private fun setupWithDeaths(killedPlayers: Array<String>){

        val names = killedPlayers.joinToString(", ")
        //todo: take these values from rounds.json/ action.json so they can be customized (need to add it)
        binding.tvRoundResult.text = resources.getQuantityString(
            R.plurals.round_deaths,
            killedPlayers.size,
            names
        )
    }

    private fun setupNoDeaths(){
        binding.tvRoundResult.text = getString(R.string.round_no_deaths)
    }

}
