package com.onewisebit.scpescape.game.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.onewisebit.scpescape.databinding.FragmentVoteTurnBinding
import com.onewisebit.scpescape.fsm.actions.Action
import com.onewisebit.scpescape.game.GameContract
import com.onewisebit.scpescape.game.VoteContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

/**
 * A simple [Fragment] subclass.
 */
class VoteTurnFragment(gameID: Long, private val onActionListener: (action: Action) -> Unit) :
    BaseGameFragment(gameID, onActionListener), VoteContract.VoteView {

    private val presenter: VoteContract.VotePresenter by inject { parametersOf(this) }

    private var _binding: FragmentVoteTurnBinding? = null
    private val binding get() = _binding!!

    private val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // initialize binding
        _binding = FragmentVoteTurnBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}