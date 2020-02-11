package com.onewisebit.scpescape.game

import androidx.fragment.app.Fragment
import com.onewisebit.scpescape.fsm.actions.Action

abstract class BaseGameFragment(
    val gameID: Long,
    private val onActionListener: (action: Action) -> Unit
) : Fragment()