package com.onewisebit.scpescape.game.intro.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.onewisebit.scpescape.databinding.FragmentIntroBinding
import com.onewisebit.scpescape.fsm.actions.Action
import com.onewisebit.scpescape.game.intro.IntroContract
import com.onewisebit.scpescape.game.BaseGameFragment
import com.onewisebit.scpescape.model.parsed.ModeDataClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


/**
 * A simple [Fragment] subclass.
 * Use the [SCPFragmentFactory] factory method to
 * create an instance of this fragment.
 */
class IntroFragment(gameID: Long, private val onActionListener: (action: Action) -> Unit) :
    BaseGameFragment(gameID, onActionListener), IntroContract.IntroView {

    private val presenter: IntroContract.IntroPresenter by inject { parametersOf(this, gameID) }
    private var _binding: FragmentIntroBinding? = null
    private val binding get() = _binding!!

    private val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIntroBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uiScope.launch {
            val mode: ModeDataClass? = presenter.getMode()

            if (mode != null) {
                binding.tvDescription.text = mode.description
                binding.tvRules.text = mode.rules
                binding.fabPlay.setOnClickListener {
                    uiScope.launch {
                        binding.fabPlay.isEnabled = false
                        presenter.assignRoles()
                        onActionListener(Action.StartGameClicked())
                    }
                }
            } else
                Log.d(TAG, "Mode retrieval error")
        }

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
        private val TAG = IntroFragment::class.java.simpleName
    }
}
