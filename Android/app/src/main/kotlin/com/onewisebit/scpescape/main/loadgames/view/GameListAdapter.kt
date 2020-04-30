package com.onewisebit.scpescape.main.loadgames.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onewisebit.scpescape.databinding.GameListItemBinding
import com.onewisebit.scpescape.list.NoSuchRecyclerItemType
import com.onewisebit.scpescape.model.entities.Game

/**
 * [RecyclerView.Adapter] that can display a [Game]
 */
class GameListAdapter(
    private val games: List<Game>,
    private val clickListener: (Long, Boolean) -> Unit
) : RecyclerView.Adapter<GameListAdapter.GameHolder>() {

    private var gamesList = games

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameListAdapter.GameHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        when (viewType) {
            TYPE_GAME -> {
                val gameBinding = GameListItemBinding.inflate(layoutInflater, parent, false)
                return GameHolder(gameBinding)
            }
            else -> throw NoSuchRecyclerItemType("No correspondent binding found for viewType $viewType")
        }
    }

    override fun getItemCount(): Int = gamesList.size

    override fun onBindViewHolder(holder: GameListAdapter.GameHolder, position: Int) {
        val item = gamesList[position]
        holder.bind(item, clickListener)
    }

    fun setGames(games: List<Game>) {
        gamesList = games
        notifyDataSetChanged()
    }

    inner class GameHolder(private val gBinding: GameListItemBinding) :
        RecyclerView.ViewHolder(gBinding.root) {

        private var game: Game? = null

        fun bind(
            _game: Game,
            _clickListener: (Long, Boolean) -> Unit
        ) {
            game = _game
            gBinding.apply {
                this.gameId.text = "ID: ${_game.id}"
                this.mode.text = "Mode: ${_game.modeID}"
                this.type.text = "Type: ${_game.type}"
                gBinding.rootLayout.setOnClickListener { _clickListener(_game.id, true) }
            }
        }
    }

    companion object {
        private val TAG = GameListAdapter::class.java.simpleName
        const val TYPE_GAME: Int = 0
    }
}