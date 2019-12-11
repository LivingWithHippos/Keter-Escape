package com.onewisebit.scpescape.newgamesettings.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.onewisebit.scpescape.R
import com.onewisebit.scpescape.databinding.FragmentNewGameSettingsBinding
import com.onewisebit.scpescape.model.entities.Mode
import com.onewisebit.scpescape.newgamesettings.GameSettingsContract
import com.onewisebit.scpescape.utilities.GAME_CLASSIC_MAX_PLAYERS
import com.onewisebit.scpescape.utilities.GAME_CLASSIC_MID_PLAYERS
import com.onewisebit.scpescape.utilities.GAME_CLASSIC_MIN_PLAYERS
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class NewGameSettingsFragment : Fragment(), GameSettingsContract.GameSettingsView {
    private lateinit var binding: FragmentNewGameSettingsBinding
    private val presenter: GameSettingsContract.GameSettingsPresenter by inject { parametersOf(this) }
    private val args: NewGameSettingsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewGameSettingsBinding.inflate(layoutInflater)

        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getMode(args.gameMode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it != null)
                        setupView(view, it)
                    else
                        Log.d(TAG, "Retrieved Mode was null: ${args.gameMode}")
                },
                { Log.d(TAG, "Mode retrieval Error",it) }
            )
    }


    private fun setupView(view: View, mode: Mode) {
        //TODO: load values from mode
        binding.tvRoles.setText(R.string.mode_classic_players_lower)
        binding.npPlayerPicker.minValue = mode.min
        binding.npPlayerPicker.maxValue = mode.max
        binding.npPlayerPicker.setOnValueChangedListener { _, _, newVal ->
            when (newVal) {
                in GAME_CLASSIC_MIN_PLAYERS..GAME_CLASSIC_MID_PLAYERS -> binding.tvRoles.setText(R.string.mode_classic_players_lower)
                in GAME_CLASSIC_MID_PLAYERS + 1..GAME_CLASSIC_MAX_PLAYERS -> binding.tvRoles.setText(
                    R.string.mode_classic_players_higher
                )
                //TODO: make this clickable to report feedback
                else -> binding.tvRoles.setText(R.string.players_count_error)
            }
        }

        binding.fabChoosePlayers.setOnClickListener {
            val gameID: Single<Long> = presenter.getNewGame(args.gameMode, args.gameType)
            gameID.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        val action =
                            NewGameSettingsFragmentDirections.actionNewGameSettingsToParticipantsChoice(
                                binding.npPlayerPicker.value,
                                it
                            )
                        view.findNavController().navigate(action)
                    },
                    { Log.d("NewGameSettingsFragment", "Insert Game Error") }
                )
        }
    }

    companion object {
        private val TAG = NewGameSettingsFragment::class.java.simpleName
    }

}