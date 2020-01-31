package com.onewisebit.scpescape.game.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.onewisebit.scpescape.databinding.FragmentDayNightBinding
import com.onewisebit.scpescape.fsm.actions.Action
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

//TODO: rename this to RoundSwitchFragment. This fragment will be shown between rounds an will describe what is going to happen in the next round. Also rename the other day/light pieces.
class RoundInfoFragment(gameID: Long, onActionListener: (action: Action) -> Unit) :
    BaseGameFragment(gameID, onActionListener) {

    private var _binding: FragmentDayNightBinding? = null
    private val binding get() = _binding!!

    private val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDayNightBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    companion object {
        private val TAG = RoundInfoFragment::class.java.simpleName
    }
}