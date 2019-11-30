package com.onewisebit.scp_scarycontainmentpanic

import android.os.Bundle
import com.onewisebit.scp_scarycontainmentpanic.StartContract.StartView
import com.onewisebit.scp_scarycontainmentpanic.databinding.ActivityMainBinding
import com.onewisebit.scp_scarycontainmentpanic.model.StartActivityModel
import com.onewisebit.scp_scarycontainmentpanic.presenters.StartActivityPresenterImpl
import org.koin.android.ext.android.inject

class MainActivity : BaseSCPActivity(), StartView, CreatePlayerDialogFragment.NewPlayerDialogListener {

    private lateinit var presenter: StartActivityPresenterImpl
    private lateinit var binding: ActivityMainBinding
    private val model : StartActivityModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = StartActivityPresenterImpl(this, model)
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
