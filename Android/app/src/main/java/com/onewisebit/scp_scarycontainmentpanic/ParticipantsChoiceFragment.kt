package com.onewisebit.scp_scarycontainmentpanic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.onewisebit.scp_scarycontainmentpanic.databinding.FragmentParticipantsChoiceBinding
import com.onewisebit.scp_scarycontainmentpanic.model.Player
import com.onewisebit.scp_scarycontainmentpanic.model.PlayersModelImpl
import com.onewisebit.scp_scarycontainmentpanic.presenters.PlayersPresenterImpl
import org.koin.android.ext.android.inject

class ParticipantsChoiceFragment : Fragment(), PlayersContract.PlayersView {

    private lateinit var layoutManager: GridLayoutManager
    private lateinit var adapter: ParticipantsAdapter
    private lateinit var binding: FragmentParticipantsChoiceBinding
    private lateinit var presenter: PlayersContract.PlayersPresenter
    //TODO: check if the model needs to be the interface and not the implementation
    private val model: PlayersModelImpl by inject()
    private val args: ParticipantsChoiceFragmentArgs by navArgs()

    private var playersList: ArrayList<Player> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentParticipantsChoiceBinding.inflate(layoutInflater)
        layoutManager = GridLayoutManager(this.context, 2)
        presenter = PlayersPresenterImpl(this,model)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO: change remaining players as they're selected
        binding.tvSelectPlayersTitle.text =
            getString(R.string.select_players, args.totPlayers, args.totPlayers)
        binding.rvPlayers.layoutManager = layoutManager
        binding.bCreatePlayer.setOnClickListener{
            showCreatePlayerDialog()
        }
    }

    override fun initView(players: ArrayList<Player>) {
        playersList = players
    }

    fun showCreatePlayerDialog(){
        val newPlayerDialog = CreatePlayerDialogFragment()
        fragmentManager?.let { newPlayerDialog.show(it, "CreatePlayerDialogFragment") }
    }

}