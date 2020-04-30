package com.onewisebit.scpescape.fsm.states

import com.onewisebit.scpescape.fsm.actions.Action

interface StateGame {
    fun consumeAction(action: Action): StateGame

    fun confrontName(name: String): Boolean {
        return getName().equals(name, ignoreCase = true)
    }

    //todo: check if this returns the correct class or the interface
    fun getName(): String {
        return this::class.java.simpleName
    }
}

class IllegalGameStateTransition(message: String) : IllegalStateException(message)