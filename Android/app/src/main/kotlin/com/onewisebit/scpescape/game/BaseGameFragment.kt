package com.onewisebit.scpescape.game

import androidx.viewbinding.ViewBinding
import com.onewisebit.scpescape.BaseSCPFragment
import com.onewisebit.scpescape.fsm.actions.Action

abstract class BaseGameFragment<T : ViewBinding>(
    val gameID: Long,
    private val onActionListener: (action: Action) -> Unit
): BaseSCPFragment<T>() {
}