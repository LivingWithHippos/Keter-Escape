package com.onewisebit.scpescape.game.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.onewisebit.scpescape.fsm.states.StateGame

class SCPFragmentFactory(private val gameID: Long,private var state:StateGame): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            IntroFragment::class.java.name -> IntroFragment(gameID, state)
            else -> super.instantiate(classLoader, className)
        }
    }
}