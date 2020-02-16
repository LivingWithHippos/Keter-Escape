package com.onewisebit.scpescape.game.vote.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onewisebit.scpescape.databinding.VoteListItemBinding
import com.onewisebit.scpescape.main.playerslist.view.NoSuchRecyclerItemType
import com.onewisebit.scpescape.model.parsed.VoteParticipant
import com.onewisebit.scpescape.utilities.TYPE_VOTE

class VoteAdapter(
    voteParticipants: List<VoteParticipant>,
    private val clickListener: (Long) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var voteParticipants: List<VoteParticipant> = voteParticipants

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        when (viewType) {
            TYPE_VOTE -> {
                val voteBinding =
                    VoteListItemBinding.inflate(layoutInflater, parent, false)
                return VoteHolder(voteBinding)
            }
            else -> throw NoSuchRecyclerItemType("No correspondent binding found for viewType $viewType")
        }
    }

    override fun getItemCount(): Int = voteParticipants.size

    override fun getItemViewType(position: Int): Int {
        var type = TYPE_VOTE
        if (voteParticipants.size > position )
            type = voteParticipants[position].type
        return type
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item: VoteParticipant = voteParticipants[position]
        if (holder is VoteHolder) {
            holder.bind(item, clickListener)
        }
    }

    fun updateLists(_voteParticipants: List<VoteParticipant>) {
        voteParticipants = _voteParticipants
        notifyDataSetChanged()
    }

    companion object {
        private val TAG = VoteAdapterBackup::class.java.simpleName
    }

    class VoteHolder(vBinding: VoteListItemBinding) :
        RecyclerView.ViewHolder(vBinding.root){

        private var voteParticipant: VoteParticipant? = null
        private val binding: VoteListItemBinding = vBinding

        fun bind(_voteParticipant: VoteParticipant, _clickListener: (Long) -> Unit) {
            voteParticipant = _voteParticipant

            //TODO: add players pics

            binding.tvName.text = _voteParticipant.playerName

            if (_voteParticipant.revealRole) {
                binding.tvRole.text = _voteParticipant.participant.roleName
                binding.tvRole.visibility = View.VISIBLE
            }

            if (_voteParticipant.revealVote && !_voteParticipant.votedPlayers.isNullOrEmpty()) {
                var voted = ""
                _voteParticipant.votedPlayers!!.forEach { voted = voted.plus(it.name).plus(", ") }
                binding.tvVotedPlayerName.text = voted
                binding.llVoted.visibility = View.VISIBLE
            }

            if (_voteParticipant.enabledVote) {
                binding.cvVotePlayer.isCheckable = true
                binding.cvVotePlayer.isClickable = true
                binding.cvVotePlayer.setOnClickListener { _clickListener(_voteParticipant.participant.playerID) }
            }
        }
    }
}