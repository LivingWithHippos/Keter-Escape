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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

/**
 * A simple [Fragment] subclass.
 */
class InfoTurnFragment(gameID: Long, private val onActionListener: (action: Action) -> Unit) :
    BaseGameFragment(gameID, onActionListener) {


    private var _binding: FragmentInfoTurnBinding? = null
    private val binding get() = _binding!!

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
        return binding.root
    }

}