package com.onewisebit.scpescape.playerslist.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.onewisebit.scpescape.R
import com.onewisebit.scpescape.databinding.FragmentParticipantsChoiceBinding
import com.onewisebit.scpescape.utilities.SearchableObservable
import com.onewisebit.scpescape.model.entities.Player
import com.onewisebit.scpescape.playerslist.PlayersContract
import com.onewisebit.scpescape.utilities.hideKeyboard
import io.reactivex.Flowable
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
    private val presenter: PlayersContract.PlayersPresenter by inject { parametersOf(this) }
    private val args: ParticipantsChoiceFragmentArgs by navArgs()

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
        ) { id: Long, add: Boolean ->playerToggle(id, add) }
        enablePlayerSearch()
        binding.root.setOnClickListener{ binding.root.hideKeyboard() }
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
                            ParticipantsChoiceFragmentDirections.actionParticipantsChoiceToGameActivity(args.gameID)
                        view.findNavController().navigate(action)
                    },
                    { Log.d(TAG, "Error while setting game permanent") }
                )
        }
        presenter.setPlayers(args.gameID)
    }

    override fun initView(players: Flowable<List<Player>>, participants: Flowable<List<Long>>) {
        players.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { adapter.setPlayers(it) },
                { Log.d(TAG, "Players List retrieval error") }
            )

        participants.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    adapter.setParticipants(it)
                    binding.tvSelectPlayersTitle.text =
                        getString(R.string.select_players, args.totPlayers, args.totPlayers - it.size)
                },
                { Log.d(TAG, "Participants List retrieval error") }
            )
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
        //TODO: add coorect button visibility on fragment resume/back
        if (add) {
            presenter.getParticipantsNumber(args.gameID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        if(it<args.totPlayers){
                            presenter.addParticipant(args.gameID, id)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                    { Log.d(TAG, "Participant Insert Success")
                                        if(it >= args.totPlayers -1)
                                            binding.fabStartGame.visibility = View.VISIBLE
                                    },
                                    { Log.d(TAG, "Participant Insert Error") }
                                )
                        }
                        else
                            Toast.makeText(context,getString(R.string.max_players_reached),Toast.LENGTH_LONG).show()


                    },
                    { Log.d(TAG, "Can't get Participants Number") }
                )


        } else {
            presenter.removeParticipant(args.gameID, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { Log.d(TAG, "Participant removal Success")
                        binding.fabStartGame.visibility = View.INVISIBLE
                    },
                    { Log.d(TAG, "Participant removal Error") }
                )
        }
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