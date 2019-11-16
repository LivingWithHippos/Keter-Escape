package com.onewisebit.scp_scarycontainmentpanic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.onewisebit.scp_scarycontainmentpanic.databinding.FragmentNewGameSettingsBinding

class NewGameSettingsFragment:Fragment() {

    private lateinit var binding: FragmentNewGameSettingsBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args: NewGameSettingsFragmentArgs by navArgs()
        binding = FragmentNewGameSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.playerPicker.minValue=3
        binding.playerPicker.maxValue=10
    }
}