package com.onewisebit.scpescape.fsm.states

import com.onewisebit.scpescape.fsm.actions.Action

interface StateGame {
    fun consumeAction(action: Action): StateGame
}

class IllegalGameStateTransition(message: String) : IllegalStateException(message)