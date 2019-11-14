package com.onewisebit.scp_scarycontainmentpanic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.onewisebit.scp_scarycontainmentpanic.StartContract.*
import com.onewisebit.scp_scarycontainmentpanic.presenters.StartActivityPresenter

class MainActivity : BaseSCPActivity(), StartView {

    private var presenter: StartActivityPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = StartActivityPresenter(this,applicationContext)
    }


    override fun initView() {
        //TODO: apply theme at start
    }

    override fun updateTheme() {
        //TODO: update theme if changed
    }
}
