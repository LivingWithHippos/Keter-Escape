package com.onewisebit.scpescape.game.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.onewisebit.scpescape.databinding.FragmentRoundInfoBinding
import com.onewisebit.scpescape.fsm.actions.Action
import com.onewisebit.scpescape.game.RoundInfoContract
import com.onewisebit.scpescape.model.parsed.RoundDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

//TODO: rename this to RoundSwitchFragment. This fragment will be shown between rounds an will describe what is going to happen in the next round. Also rename the other day/light pieces.
class RoundInfoFragment(gameID: Long, onActionListener: (action: Action) -> Unit) :
    BaseGameFragment(gameID, onActionListener), RoundInfoContract.RoundInfoView {

    private val presenter: RoundInfoContract.RoundInfoPresenter by inject { parametersOf(this, gameID) }
    private var _binding: FragmentRoundInfoBinding? = null
    private val binding get() = _binding!!

    private val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRoundInfoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uiScope.launch {
            presenter.loadRoundInfo()
        }
    }

    override fun initView(info: RoundDetails?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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