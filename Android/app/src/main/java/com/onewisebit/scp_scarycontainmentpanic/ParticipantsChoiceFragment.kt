package com.onewisebit.scp_scarycontainmentpanic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.onewisebit.scp_scarycontainmentpanic.databinding.FragmentParticipantsChoiceBinding
import com.onewisebit.scp_scarycontainmentpanic.model.Player

class ParticipantsChoiceFragment:Fragment() {

    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var adapter: ParticipantsAdapter
    private lateinit var binding: FragmentParticipantsChoiceBinding

    private var playersList: ArrayList<Player> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentParticipantsChoiceBinding.inflate(layoutInflater)
        gridLayoutManager = GridLayoutManager(this.context,2)
        return binding.root
    }
}