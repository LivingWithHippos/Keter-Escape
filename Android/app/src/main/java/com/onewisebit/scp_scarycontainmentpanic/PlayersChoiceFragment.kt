package com.onewisebit.scp_scarycontainmentpanic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.onewisebit.scp_scarycontainmentpanic.databinding.FragmentPlayersChoiceBinding
import com.onewisebit.scp_scarycontainmentpanic.model.Player

class PlayersChoiceFragment:Fragment() {

    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var adapter: PlayersAdapter
    private lateinit var binding: FragmentPlayersChoiceBinding

    private var playersList: ArrayList<Player> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlayersChoiceBinding.inflate(layoutInflater)
        gridLayoutManager = GridLayoutManager(this.context,2)
        return binding.root
    }
}