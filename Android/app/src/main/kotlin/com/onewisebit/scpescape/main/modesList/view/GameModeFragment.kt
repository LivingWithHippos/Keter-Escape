package com.onewisebit.scpescape.main.modesList.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.onewisebit.scpescape.BaseSCPFragment
import com.onewisebit.scpescape.databinding.FragmentGameModeBinding
import com.onewisebit.scpescape.main.modesList.GameModesContract
import com.onewisebit.scpescape.model.parsed.ModeDataClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class GameModeFragment : BaseSCPFragment<FragmentGameModeBinding>(), GameModesContract.GameModesView {

    private val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: ModesAdapter
    private val args: GameModeFragmentArgs by navArgs()

    private val presenter: GameModesContract.GameModesPresenter by inject { parametersOf(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameModeBinding.inflate(layoutInflater)
        layoutManager = LinearLayoutManager(this.context)
        binding.rvModes.layoutManager = layoutManager
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uiScope.launch {
            val modes = presenter.getModes()
            adapter = ModesAdapter(modes) { id: Int -> pickMode(id) }
            binding.rvModes.adapter = adapter
        }
    }

    private fun pickMode(modeId: Int) {
        val action =
            GameModeFragmentDirections.actionGameModeToNewGameSettings(
                args.gameType,
                modeId
            )
        this.findNavController().navigate(action)
    }

    override fun setList(modes: List<ModeDataClass>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}