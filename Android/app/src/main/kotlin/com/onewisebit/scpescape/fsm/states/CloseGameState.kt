package com.onewisebit.scpescape.fsm.states

import com.onewisebit.scpescape.fsm.actions.Action

class CloseGameState : StateGame {
    override fun consumeAction(action: Action): StateGame {
        return CloseGameState()
    }
}