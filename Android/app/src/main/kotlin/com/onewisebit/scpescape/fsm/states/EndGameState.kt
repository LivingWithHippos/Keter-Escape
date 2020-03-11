package com.onewisebit.scpescape.fsm.states

import com.onewisebit.scpescape.fsm.actions.Action

class EndGameState : StateGame {
    override fun consumeAction(action: Action): StateGame {
        // todo: is there a smarter way to do this?
        return EndGameState()
    }
}