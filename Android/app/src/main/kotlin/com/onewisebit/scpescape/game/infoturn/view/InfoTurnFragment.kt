package com.onewisebit.scpescape.game.infoturn.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.onewisebit.scpescape.databinding.FragmentInfoTurnBinding
import com.onewisebit.scpescape.fsm.actions.Action
import com.onewisebit.scpescape.game.BaseGameFragment
import com.onewisebit.scpescape.utilities.ARG_ACTION_INFO_TITLE
import com.onewisebit.scpescape.utilities.ARG_ACTION_INFO_TITLE_DESCRIPTION
import com.onewisebit.scpescape.utilities.ARG_LAST_TURN
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

/**
 * A simple [Fragment] subclass.
 */
class InfoTurnFragment(gameID: Long, private val onActionListener: (action: Action) -> Unit) :
    BaseGameFragment<FragmentInfoTurnBinding>(gameID, onActionListener) {

    private val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // initialize binding
        _binding = FragmentInfoTurnBinding.inflate(layoutInflater, container, false)
        binding.tvInfoTitle.text = arguments?.getString(ARG_ACTION_INFO_TITLE)
        binding.tvInfoDescription.text = arguments?.getString(ARG_ACTION_INFO_TITLE_DESCRIPTION)

        binding.fabEndTurn.setOnClickListener {
            binding.fabEndTurn.isEnabled = false
            arguments?.let {
                if (it.getBoolean(ARG_LAST_TURN))
                    onActionListener(Action.EndRoundClicked())
                else
                    onActionListener(Action.EndTurnClicked())
            }
        }

        return binding.root
    }

}