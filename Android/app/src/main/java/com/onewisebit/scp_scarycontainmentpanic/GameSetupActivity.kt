package com.onewisebit.scp_scarycontainmentpanic

import android.os.Bundle
import androidx.navigation.navArgs
import com.onewisebit.scp_scarycontainmentpanic.databinding.ActivityGameSetupBinding

class GameSetupActivity:BaseSCPActivity() {

    private lateinit var binding:ActivityGameSetupBinding
    private var gameType: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityGameSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val args: GameSetupActivityArgs by navArgs()
        gameType = args.gameType
    }
}