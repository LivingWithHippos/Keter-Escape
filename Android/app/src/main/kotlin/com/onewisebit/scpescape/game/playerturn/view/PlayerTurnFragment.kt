package com.onewisebit.scpescape.game.playerturn.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.onewisebit.scpescape.databinding.FragmentPlayerTurnBinding
import com.onewisebit.scpescape.fsm.actions.Action
import com.onewisebit.scpescape.game.BaseGameFragment
import com.onewisebit.scpescape.game.playerturn.PlayerTurnContract
import com.onewisebit.scpescape.utilities.ARG_PLAYER_NAME
import com.onewisebit.scpescape.utilities.ARG_ROLE_DESCRIPTION
import com.onewisebit.scpescape.utilities.ARG_ROLE_NAME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

/**
 * A simple [Fragment] subclass.
 */
class PlayerTurnFragment(gameID: Long, private val onActionListener: (action: Action) -> Unit) :
    BaseGameFragment<FragmentPlayerTurnBinding>(gameID, onActionListener),
    PlayerTurnContract.PlayerTurnView {

    private val presenter: PlayerTurnContract.PlayerTurnPresenter by inject {
        parametersOf(
            this,
            gameID
        )
    }

    private val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // initialize binding
        _binding = FragmentPlayerTurnBinding.inflate(layoutInflater, container, false)

        arguments?.let {
            initView(
                it.getString(ARG_PLAYER_NAME),
                it.getString(ARG_ROLE_NAME),
                it.getString(ARG_ROLE_DESCRIPTION)
            )
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabPlayTurn.setOnClickListener {
            binding.fabPlayTurn.isEnabled = false
            onActionListener(Action.PlayTurnClicked())
        }
    }

    override fun initView(
        playerName: String?,
        playerRoleName: String?,
        playerRoleDescription: String?
    ) {
        //TODO: add player pic
        binding.tvPlayerName.text = playerName
        binding.tvPlayerRole.text = playerRoleName
        binding.tvRoleShortDescription.text = playerRoleDescription
    }
}
