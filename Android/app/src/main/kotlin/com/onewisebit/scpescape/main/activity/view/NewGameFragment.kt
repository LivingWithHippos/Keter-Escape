package com.onewisebit.scpescape.main.activity.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.onewisebit.scpescape.BaseSCPFragment
import com.onewisebit.scpescape.databinding.FragmentNewGameBinding
import com.onewisebit.scpescape.utilities.GAME_TYPE_PASS

/**
 * A simple [Fragment] subclass.
 */
class NewGameFragment : BaseSCPFragment<FragmentNewGameBinding>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewGameBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cvNewGamePass.setOnClickListener {

            val gameType = GAME_TYPE_PASS
            val action = NewGameFragmentDirections.actionNewGameToGameMode(gameType)
            view.findNavController().navigate(action)
        }
    }
}