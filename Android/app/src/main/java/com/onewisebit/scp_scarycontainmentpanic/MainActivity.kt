package com.onewisebit.scp_scarycontainmentpanic

import android.os.Bundle
import com.onewisebit.scp_scarycontainmentpanic.StartContract.StartView
import com.onewisebit.scp_scarycontainmentpanic.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : BaseSCPActivity(), StartView,
    CreatePlayerDialogFragment.NewPlayerDialogListener {

    private lateinit var binding: ActivityMainBinding
    private val presenter: StartContract.StartPresenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun updateTheme() {
        //TODO: update theme if changed
    }

    override fun onResume() {
        super.onResume()
        presenter.setView(this)
    }

    override fun onPositiveDialogClick(playerName: String) {
        presenter.addPlayer(playerName)
    }
}
