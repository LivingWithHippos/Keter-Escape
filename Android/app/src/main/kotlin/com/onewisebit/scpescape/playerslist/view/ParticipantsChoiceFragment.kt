package com.onewisebit.scpescape.playerslist.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.onewisebit.scpescape.R
import com.onewisebit.scpescape.databinding.FragmentParticipantsChoiceBinding
import com.onewisebit.scpescape.model.entities.Participant
import com.onewisebit.scpescape.model.entities.Player
import com.onewisebit.scpescape.playerslist.PlayersContract
import com.onewisebit.scpescape.utilities.SearchableObservable
import com.onewisebit.scpescape.utilities.hideKeyboard
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.util.concurrent.TimeUnit

@SuppressLint("CheckResult")
class ParticipantsChoiceFragment : Fragment(), PlayersContract.PlayersView {

    private lateinit var layoutManager: GridLayoutManager
    private lateinit var adapter: ParticipantsAdapter
    private lateinit var binding: FragmentParticipantsChoiceBinding
    private val args: ParticipantsChoiceFragmentArgs by navArgs()
    private val presenter: PlayersContract.PlayersPresenter by inject { parametersOf(this) }

    //TODO: add diffutils

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentParticipantsChoiceBinding.inflate(layoutInflater)
        layoutManager = GridLayoutManager(this.context, 2)
        adapter = ParticipantsAdapter(
            emptyList(),
            emptyList()
        ) { id: Long, add: Boolean -> playerToggle(id, add) }
        enablePlayerSearch()
        binding.root.setOnClickListener { binding.root.hideKeyboard() }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvPlayers.layoutManager = layoutManager
        binding.rvPlayers.adapter = adapter
        binding.bCreatePlayer.setOnClickListener {
            showCreatePlayerDialog()
        }
        binding.fabStartGame.setOnClickListener {

            presenter.setGameTemporary(args.gameID, false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        val action =
                            ParticipantsChoiceFragmentDirections.actionParticipantsChoiceToGameActivity(
                                args.gameID
                            )
                        view.findNavController().navigate(action)
                    },
                    { Log.d(TAG, "Error while setting game as permanent") }
                )
        }
        presenter.setPlayers(args.gameID)
    }

    override fun initView(
        players: LiveData<List<Player>>,
        participants: LiveData<List<Participant>>
    ) {

        players.observe(this, Observer<List<Player>> { playersList ->
            adapter.setPlayers(playersList)
        })

        participants.observe(this, Observer<List<Participant>> { participantsList ->
            adapter.setParticipants(participantsList.map { it.playerID })

            val totPlayers: Int = args.totPlayers
            val missingPlayers = totPlayers - participantsList.size
            binding.tvSelectPlayersTitle.text =
                getString(R.string.select_players, totPlayers, missingPlayers)

            if (participantsList.size == args.totPlayers)
                binding.fabStartGame.visibility = View.VISIBLE
            else
                binding.fabStartGame.visibility = View.INVISIBLE
        })
    }

    override fun tooManyParticipants() {
        Toast.makeText(context, getString(R.string.max_players_reached), Toast.LENGTH_LONG).show()
    }

    private fun enablePlayerSearch() {
        SearchableObservable().fromView(binding.svPlayerFilter)
            //run only if input stops for 500 ms
            .debounce(500, TimeUnit.MILLISECONDS)
            // run only if it has changed since last time
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { text -> adapter.setPlayersNameFilter(text) }
    }

    private fun playerToggle(id: Long, add: Boolean) {
        presenter.addRemoveParticipant(args.gameID, id, add, args.totPlayers)
    }

    private fun showCreatePlayerDialog() {
        val newPlayerDialog =
            CreatePlayerDialogFragment()
        fragmentManager?.let { newPlayerDialog.show(it, "CreatePlayerDialogFragment") }
    }

    companion object {
        private val TAG = ParticipantsAdapter::class.java.simpleName
    }
}