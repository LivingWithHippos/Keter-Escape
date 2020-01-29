package com.onewisebit.scpescape.game.view

import androidx.fragment.app.Fragment
import com.onewisebit.scpescape.fsm.states.StateGame

abstract class BaseGameFragment(val gameID: Long, var state: StateGame): Fragment() {

}