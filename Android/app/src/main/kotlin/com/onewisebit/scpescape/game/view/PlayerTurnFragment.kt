package com.onewisebit.scpescape.game.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.onewisebit.scpescape.databinding.FragmentPlayerTurnBinding
import com.onewisebit.scpescape.fsm.actions.Action
import com.onewisebit.scpescape.game.PlayerTurnContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

/**
 * A simple [Fragment] subclass.
 */
class PlayerTurnFragment (gameID: Long, private val onActionListener: (action: Action) -> Unit) :
BaseGameFragment(gameID, onActionListener), PlayerTurnContract.PlayerTurnView {

    private val presenter: PlayerTurnContract.PlayerTurnPresenter by inject { parametersOf(this, gameID) }

    private var _binding : FragmentPlayerTurnBinding? = null
    private val binding get() = _binding!!

    private val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // initialize binding
        _binding = FragmentPlayerTurnBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uiScope.launch {
            presenter.loadValues()
        }
    }

    override fun initView(playerName: String, playerRoleName: String, playerRoleDescription : String){
        //TODO: add player pic
        binding.tvPlayerName.text = playerName
        binding.tvPlayerRole.text = playerRoleName
        binding.tvRoleShortDescription.text = playerRoleDescription
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
