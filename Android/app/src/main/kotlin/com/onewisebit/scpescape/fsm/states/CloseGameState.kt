package com.onewisebit.scpescape.fsm.states

import com.onewisebit.scpescape.fsm.actions.Action
import com.onewisebit.scpescape.utilities.DAY
import com.onewisebit.scpescape.utilities.NIGHT

class CloseGameState  : StateGame {
    override fun consumeAction(action: Action): StateGame {
        return CloseGameState()
    }
}