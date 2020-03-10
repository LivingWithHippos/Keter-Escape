package com.onewisebit.scpescape.game

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.onewisebit.scpescape.fsm.actions.Action
import com.onewisebit.scpescape.game.infoturn.view.InfoTurnFragment
import com.onewisebit.scpescape.game.intro.view.IntroFragment
import com.onewisebit.scpescape.game.passdevice.view.PassDeviceFragment
import com.onewisebit.scpescape.game.playerturn.view.PlayerTurnFragment
import com.onewisebit.scpescape.game.roundinfo.view.RoundInfoFragment
import com.onewisebit.scpescape.game.roundresult.view.RoundResultFragment
import com.onewisebit.scpescape.game.vote.view.VoteTurnFragment

class SCPFragmentFactory(
    private val gameID: Long,
    private val onActionListener: (action: Action) -> Unit
) : FragmentFactory() {


    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            IntroFragment::class.java.name -> IntroFragment(
                gameID,
                onActionListener
            )
            RoundInfoFragment::class.java.name -> RoundInfoFragment(
                gameID,
                onActionListener
            )
            PassDeviceFragment::class.java.name -> PassDeviceFragment(
                gameID,
                onActionListener
            )
            PlayerTurnFragment::class.java.name -> PlayerTurnFragment(
                gameID,
                onActionListener
            )
            InfoTurnFragment::class.java.name -> InfoTurnFragment(
                gameID,
                onActionListener
            )
            VoteTurnFragment::class.java.name -> VoteTurnFragment(
                gameID,
                onActionListener
            )
            RoundResultFragment::class.java.name -> VoteTurnFragment(
                gameID,
                onActionListener
            )
            else -> super.instantiate(classLoader, className)
        }
    }
}