package com.onewisebit.scp_scarycontainmentpanic

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onewisebit.scp_scarycontainmentpanic.databinding.PlayerListItemBinding
import com.onewisebit.scp_scarycontainmentpanic.model.Player

class ParticipantsAdapter(playersList: ArrayList<Player>) :
    RecyclerView.Adapter<ParticipantsAdapter.PlayerHolder>() {

    private val players: ArrayList<Player> = playersList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerHolder {
        //Inflate is created from Extensions.kt
        val inflatedView = parent.inflate(R.layout.player_list_item, false)
        return PlayerHolder(inflatedView)
    }

    override fun getItemCount(): Int = players.size

    override fun onBindViewHolder(holder: PlayerHolder, position: Int) {
        val itemPlayer = players[position]
        holder.bind(itemPlayer)
    }

    companion object {
        private val TAG = ParticipantsAdapter::class.java.simpleName
    }

    class PlayerHolder(_view: View) : RecyclerView.ViewHolder(_view), View.OnClickListener {

        private var view: View = _view
        private var player: Player? = null
        private var binding: PlayerListItemBinding

        init {
            // view.setOnClickListener(this)
            //TODO: test if this works
            binding = PlayerListItemBinding.bind(view)
            binding.cvPlayer.setOnClickListener(this)
        }

        fun bind(_player: Player) {
            player = _player
        }

        override fun onClick(v: View?) {
            //TODO: open dialog to choose or create a new player
            Log.d("PlayerHolder", "no touchie")
        }

    }
}

