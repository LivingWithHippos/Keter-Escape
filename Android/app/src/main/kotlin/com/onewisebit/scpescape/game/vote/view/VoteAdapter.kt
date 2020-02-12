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
import com.onewisebit.scpescape.model.entities.Participant
import com.onewisebit.scpescape.model.entities.Player
import com.onewisebit.scpescape.model.entities.Vote

class VoteAdapter(
    playersList: List<Player>,
    participantsList: List<Participant>,
    votesList: List<Vote>,
    votedPlayersList: List<Player>,
    enabledPlayersList: List<Long>,
    private val clickListener: (Long) -> Unit) :
            ListAdapter<RecyclerItem,
            RecyclerView.ViewHolder>(BASE_DIFF_CALLBACK) {

    //todo: implement here

    // list of players to be shown, cannot be null, base on "show" from vote.json
    private var players: List<Player> = playersList
    // list of roles to be shown, can be null, base on "reveal_role" from vote.json
    private var participants: List<Participant> = participantsList
    // list of votes to be shown, can be null, base on "reveal_vote" from vote.json
    private var votes: List<Vote> = votesList
    // list of players voted, must correspond to player voted in vote list
    private var votedPlayers: List<Player> = votedPlayersList
    // list of player id that can be selected, can be null, based on "choice_enabled" from vote.json
    private var enabledPlayers: List<Long> = enabledPlayersList

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

    fun updateLists(playersList: List<Player>,
                    participantsList: List<Participant>,
                    votesList: List<Vote>,
                    votedPlayersList: List<Player>,
                    enabledPlayersList: List<Long>){
        players = playersList
        participants = participantsList
        votes = votesList
        votedPlayers = votedPlayersList
        enabledPlayers = enabledPlayersList
        notifyDataSetChanged()
    }

    // should be unneeded
    // override fun getItemCount(): Int = players.size

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return item.type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val player: Player = players[position]
        val participant: Participant? = participants.firstOrNull { it.playerID == player.id }
        val vote: Vote? = votes.firstOrNull { it.playerID == player.id }
        val votedPlayer: Player? = votedPlayers.firstOrNull { it.id == vote?.votedPlayerID ?: -1 }
        val checkable: Boolean = enabledPlayers.contains(player.id)

        if (holder is VoteHolder) {
            holder.bind(player, participant, votedPlayer,checkable, clickListener)
        }

    }

    companion object {
        private val TAG = VoteAdapter::class.java.simpleName
        const val TYPE_VOTE: Int = 0
    }

    class VoteHolder(vBinding: VoteListItemBinding) :
        RecyclerView.ViewHolder(vBinding.root),
        RecyclerItem {

        private var player: Player? = null
        private var participant: Participant? = null
        private var votedPlayer: Player? = null

        private val binding: VoteListItemBinding = vBinding

        fun bind(_player: Player, _participant: Participant?, _votedPlayer: Player?, checkable: Boolean = false, _clickListener: (Long) -> Unit) {
            player = _player
            participant = _participant
            votedPlayer = _votedPlayer

            //TODO: add players pics

            binding.tvName.text = _player.name
            _participant?.let {
                binding.tvRole.text = it.roleName
                binding.tvRole.visibility = View.VISIBLE
            }
            _votedPlayer?.let {
                binding.tvVotedPlayerName.text = _votedPlayer.name
                binding.llVoted.visibility = View.VISIBLE
            }

            if (checkable) {
                binding.cvVotePlayer.isCheckable = checkable
                binding.cvVotePlayer.setOnClickListener { _clickListener(_player.id) }
            }
        }

        // type of ViewHolder
        override val type: Int = TYPE_VOTE
        // Will be used in the DiffUtil areItemsTheSame function
        override val id: String? = player?.id.toString()

        // Will be used in the DiffUtil areContentsTheSame function, called only if areItemsTheSame is true
        override fun equals(other: Any?): Boolean {

            return (other is VoteHolder) &&
                    (participant?.roleName == other.participant?.roleName) &&
                    (votedPlayer?.name == other.votedPlayer?.name)
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