package com.onewisebit.scpescape.fsm.states

import com.onewisebit.scpescape.fsm.actions.Action

class IntroState: StateGame {
    override fun consumeAction(action: Action): StateGame {
        return when (action) {
            is Action.StartGameClicked -> DayNightState()
            else -> throw IllegalStateException("Invalid action: $action passed to an Intro state")
        }
    }
}