package com.onewisebit.scpescape.game.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.onewisebit.scpescape.fsm.actions.Action

class SCPFragmentFactory(
    private val gameID: Long,
    private val onActionListener: (action: Action) -> Unit
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            IntroFragment::class.java.name -> IntroFragment(gameID, onActionListener)
            RoundInfoFragment::class.java.name -> RoundInfoFragment(gameID, onActionListener)
            else -> super.instantiate(classLoader, className)
        }
    }
}