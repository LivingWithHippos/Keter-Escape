package com.onewisebit.scpescape.newgamesettings.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.onewisebit.scpescape.databinding.FragmentGameModeBinding

class GameModeFragment: Fragment() {
    private lateinit var binding : FragmentGameModeBinding
    private val args: GameModeFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameModeBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cvGameModeClassic.setOnClickListener{
            //TODO: decide how to identify game mode ( name, id etc)
            val action =
                GameModeFragmentDirections.actionGameModeToNewGameSettings(
                    args.gameType,
                    0
                )
            view.findNavController().navigate(action)
        }
    }
}