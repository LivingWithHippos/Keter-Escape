package com.onewisebit.scp_scarycontainmentpanic

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.onewisebit.scp_scarycontainmentpanic.databinding.FragmentParticipantsChoiceBinding
import com.onewisebit.scp_scarycontainmentpanic.model.Player
import com.onewisebit.scp_scarycontainmentpanic.model.PlayersModelImpl
import com.onewisebit.scp_scarycontainmentpanic.presenters.PlayersPresenterImpl
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class ParticipantsChoiceFragment : Fragment(), PlayersContract.PlayersView {

    private lateinit var layoutManager: GridLayoutManager
    private lateinit var adapter: ParticipantsAdapter
    private lateinit var binding: FragmentParticipantsChoiceBinding
    private val presenter: PlayersContract.PlayersPresenter by inject{ parametersOf(this) }
    private val args: ParticipantsChoiceFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentParticipantsChoiceBinding.inflate(layoutInflater)
        layoutManager = GridLayoutManager(this.context, 2)
        adapter = ParticipantsAdapter(emptyList())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO: change remaining players as they're selected
        binding.tvSelectPlayersTitle.text =
            getString(R.string.select_players, args.totPlayers, args.totPlayers)
        binding.rvPlayers.layoutManager = layoutManager
        binding.rvPlayers.adapter = adapter
        binding.bCreatePlayer.setOnClickListener{
            showCreatePlayerDialog()
        }
        presenter.setPlayers()
    }

    override fun initView(players: Flowable<List<Player>>) {
        players.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { adapter.setPlayers(it) },
                { Log.d(TAG, "Players List retrieval error") }
            )
    }

    fun showCreatePlayerDialog(){
        val newPlayerDialog = CreatePlayerDialogFragment()
        fragmentManager?.let { newPlayerDialog.show(it, "CreatePlayerDialogFragment") }
    }

    companion object {
        private val TAG = ParticipantsAdapter::class.java.simpleName
    }
}