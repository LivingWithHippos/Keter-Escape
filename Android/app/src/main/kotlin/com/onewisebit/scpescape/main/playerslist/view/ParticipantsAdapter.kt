package com.onewisebit.scpescape.main.playerslist.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onewisebit.scpescape.databinding.ParticipantListItemBinding
import com.onewisebit.scpescape.databinding.PlayerListItemBinding
import com.onewisebit.scpescape.model.entities.Player


class ParticipantsAdapter(
    playersList: List<Player>, participantsID: List<Long>,
    private val clickListener: (Long, Boolean) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //todo: check if something like VoteParticipant can be used
    private var fullPlayers: List<Player> = playersList
    private var players: List<Player> = playersList

    private var participants: List<Long> = participantsID

    private var nameFilter: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        when (viewType) {
            TYPE_PARTICIPANT -> {
                val participantBinding =
                    ParticipantListItemBinding.inflate(layoutInflater, parent, false)
                return ParticipantHolder(
                    participantBinding
                )
            }
            TYPE_PLAYER -> {
                val playerBinding = PlayerListItemBinding.inflate(layoutInflater, parent, false)
                return PlayerHolder(
                    playerBinding
                )
            }
            else -> throw NoSuchRecyclerItemType("No correspondent binding found for viewType $viewType")
        }

    }

    override fun getItemCount(): Int = players.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = players[position]
        
        if (holder is ParticipantHolder)
            holder.bind(item, clickListener)
        if (holder is PlayerHolder)
            holder.bind(item, clickListener)
    }

    /**
     * Set a list of players items.
     */
    fun setPlayers(newPlayers: List<Player>) {
        fullPlayers = newPlayers
        updatePlayers()
    }

    fun setParticipants(ids: List<Long>) {
        participants = ids
        notifyDataSetChanged()
    }

    fun setPlayersNameFilter(name: String) {
        nameFilter = name
        updatePlayers()
    }

    private fun updatePlayers() {
        players = if (nameFilter.isNullOrBlank())
            fullPlayers
        else
            fullPlayers
                .filter { it.name.contains(nameFilter!!, ignoreCase = true) }

        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        if (participants.any { it == players[position].id })
            return TYPE_PARTICIPANT
        return TYPE_PLAYER
    }

    companion object {
        private val TAG = ParticipantsAdapter::class.java.simpleName
        const val TYPE_PLAYER: Int = 0
        const val TYPE_PARTICIPANT: Int = 1
    }

    class PlayerHolder(pBinding: PlayerListItemBinding) : RecyclerView.ViewHolder(pBinding.root) {

        private var player: Player? = null
        private val binding: PlayerListItemBinding = pBinding

        fun bind(_player: Player, _clickListener: (Long, Boolean) -> Unit) {
            player = _player
            binding.tvPlayerName.text = _player.name
            binding.cvPlayer.setOnClickListener { _clickListener(_player.id, true) }
        }

    }


    class ParticipantHolder(pBinding: ParticipantListItemBinding) :
        RecyclerView.ViewHolder(pBinding.root) {

        private var player: Player? = null
        private val binding: ParticipantListItemBinding = pBinding

        fun bind(_player: Player, _clickListener: (Long, Boolean) -> Unit) {
            player = _player
            binding.tvParticipantName.text = _player.name
            binding.cvParticipant.setOnClickListener { _clickListener(_player.id, false) }
        }

    }
}

class NoSuchRecyclerItemType(message: String) : RuntimeException(message)
