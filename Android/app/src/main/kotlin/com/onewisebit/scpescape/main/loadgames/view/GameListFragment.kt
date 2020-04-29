package com.onewisebit.scpescape.main.loadgames.view

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.onewisebit.scpescape.BaseSCPFragment
import com.onewisebit.scpescape.databinding.FragmentGameListBinding
import com.onewisebit.scpescape.main.loadgames.GamesListContract
import com.onewisebit.scpescape.model.entities.Game
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

/**
 * A fragment representing a list of Items.
 */
class GameListFragment : BaseSCPFragment<FragmentGameListBinding>(), GamesListContract.GamesListView {

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: GameListAdapter
    private val presenter: GamesListContract.GamesListPresenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameListBinding.inflate(layoutInflater)
        layoutManager = LinearLayoutManager(context)
        adapter = GameListAdapter(emptyList())
            { id: Long, action: Boolean -> onGameClicked(id, action) }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.list.layoutManager = layoutManager
        binding.list.adapter = adapter
        presenter.initializeGamesList()
    }

    override fun initView(games: LiveData<List<Game>>) {
        games.observe(this, Observer<List<Game>> { gamesList ->
            adapter.setGames(gamesList)
        })
    }

    private fun onGameClicked(gameID: Long, action: Boolean) {}

    companion object {
        private val TAG = GameListFragment::class.java.simpleName
    }

}