package com.onewisebit.scp_scarycontainmentpanic

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.onewisebit.scp_scarycontainmentpanic.databinding.FragmentParticipantsChoiceBinding
import com.onewisebit.scp_scarycontainmentpanic.model.Participant
import com.onewisebit.scp_scarycontainmentpanic.model.Player
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
        adapter = ParticipantsAdapter(emptyList(), emptyList()) { id: Long, add: Boolean ->
            playerToggle(
                id,
                add
            )
        }
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
        onParticipantUpdated()
        presenter.setPlayers(args.gameID)
    }

    private fun onParticipantUpdated() {
        presenter.getParticipantsNumber(args.gameID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    binding.tvSelectPlayersTitle.text =
                        getString(R.string.select_players, args.totPlayers, args.totPlayers - it)
                },
                { Log.d(TAG, "Can't get Participants Number") }
            )

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
                { adapter.setParticipants(it) },
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
        if (add) {
            presenter.addParticipant(args.gameID, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { Log.d(TAG, "Participant Insert Success") },
                    { Log.d(TAG, "Participant Insert Error") }
                )
        } else {
            presenter.removeParticipant(args.gameID, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { Log.d(TAG, "Participant removal Success") },
                    { Log.d(TAG, "Participant removal Error") }
                )
        }
        onParticipantUpdated()
    }

    private fun showCreatePlayerDialog() {
        val newPlayerDialog = CreatePlayerDialogFragment()
        fragmentManager?.let { newPlayerDialog.show(it, "CreatePlayerDialogFragment") }
    }

    companion object {
        private val TAG = ParticipantsAdapter::class.java.simpleName
    }
}