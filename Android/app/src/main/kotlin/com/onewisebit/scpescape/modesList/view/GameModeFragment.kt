package com.onewisebit.scpescape.modesList.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.onewisebit.scpescape.databinding.FragmentGameModeBinding
import com.onewisebit.scpescape.model.ModeDataClass
import com.onewisebit.scpescape.modesList.GameModesContract
import com.onewisebit.scpescape.newgamesettings.view.GameModeFragmentArgs
import com.onewisebit.scpescape.newgamesettings.view.GameModeFragmentDirections
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class GameModeFragment: Fragment(), GameModesContract.GameModesView {

    private lateinit var binding : FragmentGameModeBinding
    private val args: GameModeFragmentArgs by navArgs()

    private val presenter: GameModesContract.GameModesPresenter by inject { parametersOf(this) }

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

    override fun setList(modes: List<ModeDataClass>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}