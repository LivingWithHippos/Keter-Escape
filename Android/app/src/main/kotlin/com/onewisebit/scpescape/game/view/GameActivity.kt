package com.onewisebit.scpescape.game.view

import android.os.Bundle
import com.onewisebit.scpescape.BaseSCPActivity
import com.onewisebit.scpescape.databinding.ActivityGameBinding
import com.onewisebit.scpescape.game.GameContract
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class GameActivity: BaseSCPActivity(), GameContract.GameView{

    private lateinit var binding: ActivityGameBinding
    private val presenter: GameContract.GamePresenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
