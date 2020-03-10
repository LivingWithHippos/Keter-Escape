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
import com.onewisebit.scpescape.game.vote.VoteTurnContract
import com.onewisebit.scpescape.model.parsed.VoteParticipant
import com.onewisebit.scpescape.utilities.ARG_LAST_TURN
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
    BaseGameFragment<FragmentVoteTurnBinding>(gameID, onActionListener), VoteTurnContract.VoteTurnView {

    // todo: this is null here, needs to be called later or replace with navigationarguments()
    //private val args = arguments

    private val presenter: VoteTurnContract.VoteTurnPresenter by inject {
        parametersOf(
            this,
            gameID
        )
    }

    private lateinit var roleName: String
    private lateinit var roundCode: String
    private var lastTurn: Boolean = false

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
        ) { id: Long, setChecked: (checked: Boolean) -> Unit -> playerVoted(id, setChecked) }

        arguments?.let {
            roleName = it.getString(ARG_ROLE_NAME)!!
            roundCode = it.getString(ARG_ROUND_CODE)!!
            lastTurn = it.getBoolean(ARG_LAST_TURN)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvVotes.layoutManager = layoutManager
        binding.rvVotes.adapter = adapter

        //todo: rework this since it is similar to InfoTurn's one
        binding.fabCastVote.setOnClickListener {
            arguments?.let {
                if (it.getBoolean(ARG_LAST_TURN))
                    onActionListener(Action.EndRoundClicked())
                else
                    onActionListener(Action.EndTurnClicked())
            }
        }

        uiScope.launch {
            presenter.loadValues(roleName, roundCode)
        }
    }

    override fun initializeList(voteParticipants: List<VoteParticipant>) {
        adapter.updateLists(voteParticipants)
    }

    private fun playerVoted(votedPlayerId: Long, setViewChecked: (Boolean) -> Unit) {
        uiScope.launch {
            //todo: rename, this function must also manage removal of the vote
            val voteAdded = presenter.setCurrentTurnVote(votedPlayerId)
            Log.d(TAG, "Voted player $id: added: $voteAdded")
            setViewChecked(voteAdded)
            //todo: move this in presenter.addCurrentTurnVote?
            presenter.checkVotes()
        }
    }

    override fun setFab(visible: Boolean) {
        binding.fabCastVote.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }

    companion object {
        private val TAG = VoteTurnFragment::class.java.simpleName
    }
}