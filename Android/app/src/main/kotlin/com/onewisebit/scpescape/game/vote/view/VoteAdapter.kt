package com.onewisebit.scpescape.game.vote.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.onewisebit.scpescape.databinding.VoteListItemBinding
import com.onewisebit.scpescape.list.RecyclerItem
import com.onewisebit.scpescape.main.playerslist.view.NoSuchRecyclerItemType
import com.onewisebit.scpescape.model.parsed.VoteParticipant

class VoteAdapter(
    voteParticipants: List<VoteParticipant>,
    private val clickListener: (Long) -> Unit
) :
    ListAdapter<RecyclerItem,
            RecyclerView.ViewHolder>(BASE_DIFF_CALLBACK) {

    //todo: implement here
    // VoteParticipants
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

    fun updateLists(_voteParticipants: List<VoteParticipant>) {
        voteParticipants = _voteParticipants
        notifyDataSetChanged()
    }

    // should be unneeded
    // override fun getItemCount(): Int = players.size

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return item.type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val participant: VoteParticipant = voteParticipants[position]
        if (holder is VoteHolder) {
            holder.bind(participant, clickListener)
        }

    }

    companion object {
        private val TAG = VoteAdapter::class.java.simpleName
        const val TYPE_VOTE: Int = 0
    }

    class VoteHolder(vBinding: VoteListItemBinding) :
        RecyclerView.ViewHolder(vBinding.root),
        RecyclerItem {

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
                binding.cvVotePlayer.setOnClickListener { _clickListener(_voteParticipant.participant.playerID) }
            }
        }

        // type of ViewHolder
        override val type: Int = TYPE_VOTE
        // Will be used in the DiffUtil areItemsTheSame function
        override val id: String? = voteParticipant?.participant?.playerID.toString()

        // Will be used in the DiffUtil areContentsTheSame function, called only if areItemsTheSame is true
        override fun equals(other: Any?): Boolean {

            return (other is VoteHolder) &&
                    (voteParticipant?.participant?.roleName == other.voteParticipant?.participant?.roleName) &&
                    (voteParticipant?.playerName == other.voteParticipant?.playerName)
        }

    }
}

val BASE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<RecyclerItem>() {

    override fun areItemsTheSame(oldItem: RecyclerItem, newItem: RecyclerItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RecyclerItem, newItem: RecyclerItem): Boolean {
        return oldItem == newItem
    }

}