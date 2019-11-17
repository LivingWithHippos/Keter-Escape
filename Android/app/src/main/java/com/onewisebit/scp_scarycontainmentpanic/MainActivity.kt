package com.onewisebit.scp_scarycontainmentpanic

import android.os.Bundle
import com.onewisebit.scp_scarycontainmentpanic.StartContract.StartView
import com.onewisebit.scp_scarycontainmentpanic.presenters.StartActivityPresenter

class MainActivity : BaseSCPActivity(), StartView {

    private var presenter: StartActivityPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = StartActivityPresenter(this, applicationContext)
    }


    override fun initView() {
        //TODO: apply theme at start
    }

    override fun updateTheme() {
        //TODO: update theme if changed
    }

    override fun onResume() {
        super.onResume()
        presenter?.setView(this)
    }
}
