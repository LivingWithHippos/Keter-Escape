package com.onewisebit.scp_scarycontainmentpanic

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onewisebit.scp_scarycontainmentpanic.databinding.PlayerListItemBinding
import com.onewisebit.scp_scarycontainmentpanic.model.Player
import android.view.LayoutInflater


class ParticipantsAdapter(playersList: List<Player>) :
    RecyclerView.Adapter<ParticipantsAdapter.PlayerHolder>() {

    private var fullPlayers: List<Player> = playersList
    private var players: List<Player> = playersList

    private var nameFilter: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val playerBinding = PlayerListItemBinding.inflate(layoutInflater, parent, false)
        return PlayerHolder(playerBinding)
    }

    override fun getItemCount(): Int = players.size

    override fun onBindViewHolder(holder: PlayerHolder, position: Int) {
        val itemPlayer = players[position]
        holder.bind(itemPlayer)
    }

    /**
     * Set a list of players items.
     */
    fun setPlayers( newPlayers:List<Player>){
        //TODO: maybe move this in the mvp structure somehow?
        fullPlayers = newPlayers
        updatePlayers()
    }

    fun setPlayersNameFilter(name: String){
        nameFilter = name
        updatePlayers()
    }

    private fun updatePlayers(){
        if (nameFilter.isNullOrBlank())
            players = fullPlayers
        else
            players= fullPlayers.filter { it.name.contains(nameFilter!!, ignoreCase = true) }
        notifyDataSetChanged()
    }


    companion object {
        private val TAG = ParticipantsAdapter::class.java.simpleName
    }

    class PlayerHolder(pBinding: PlayerListItemBinding) : RecyclerView.ViewHolder(pBinding.root), View.OnClickListener {

        private var player: Player? = null
        private val binding:PlayerListItemBinding =pBinding

        init {
            binding.cvPlayer.setOnClickListener(this)
        }

        fun bind(_player: Player) {
            player = _player
            binding.tvPlayerName.text= player!!.name
        }

        override fun onClick(v: View?) {
            //TODO: select player -> participant and change view
            Log.d("PlayerHolder", "no touchie")
        }

    }
}

