package com.onewisebit.scpescape.game.vote.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.onewisebit.scpescape.databinding.FragmentVoteTurnBinding
import com.onewisebit.scpescape.fsm.actions.Action
import com.onewisebit.scpescape.game.BaseGameFragment
import com.onewisebit.scpescape.game.vote.VoteContract
import com.onewisebit.scpescape.model.parsed.VoteParticipant
import com.onewisebit.scpescape.utilities.ARG_ROLE_NAME
import com.onewisebit.scpescape.utilities.ARG_ROUND_CODE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

/**
 * A simple [Fragment] subclass.
 */
class VoteTurnFragment(gameID: Long, private val onActionListener: (action: Action) -> Unit) :
    BaseGameFragment(gameID, onActionListener), VoteContract.VoteView {

    private val args = arguments
    private val presenter: VoteContract.VotePresenter by inject {
        parametersOf(
            this,
            gameID,
            args?.getString(ARG_ROUND_CODE),
            args?.getString(ARG_ROLE_NAME)
        )
    }

    private var _binding: FragmentVoteTurnBinding? = null
    private val binding get() = _binding!!

    private val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: VoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // initialize binding
        _binding = FragmentVoteTurnBinding.inflate(layoutInflater, container, false)

        layoutManager = LinearLayoutManager(this.context)
        adapter = VoteAdapter(
            emptyList()
        ) { id: Long -> playerVoted(id) }

        binding.rvVotes.layoutManager = layoutManager
        binding.rvVotes.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uiScope.launch {
            presenter.loadValues()
        }
    }

    override fun updateList(voteParticipants: List<VoteParticipant>) {
        adapter.updateLists(voteParticipants)
    }

    fun playerVoted(id: Long) {
        Log.d(TAG, "Voted player $id")
    }

    override fun enableFab() {
        binding.fabCastVote.visibility = View.VISIBLE
    }

    companion object {
        private val TAG = VoteTurnFragment::class.java.simpleName
    }
}