package com.onewisebit.scpescape.main.view

import android.os.Bundle
import com.onewisebit.scpescape.BaseSCPActivity
import com.onewisebit.scpescape.databinding.ActivityMainBinding
import com.onewisebit.scpescape.main.StartContract
import com.onewisebit.scpescape.main.StartContract.StartView
import com.onewisebit.scpescape.playerslist.view.CreatePlayerDialogFragment
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : BaseSCPActivity(), StartView,
    CreatePlayerDialogFragment.NewPlayerDialogListener {

    private val presenter: StartContract.StartPresenter by inject { parametersOf(this) }

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun updateTheme() {
        //TODO: update theme if changed
    }

    override fun onResume() {
        super.onResume()
        presenter.setView(this)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onPositiveDialogClick(playerName: String) {
        presenter.addPlayer(playerName)
    }
}
