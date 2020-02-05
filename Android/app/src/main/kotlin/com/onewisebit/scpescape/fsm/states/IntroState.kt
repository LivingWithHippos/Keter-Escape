package com.onewisebit.scpescape.fsm.states

import com.onewisebit.scpescape.fsm.actions.Action
import com.onewisebit.scpescape.utilities.NIGHT

class IntroState : StateGame {
    override fun consumeAction(action: Action): StateGame {
        return when (action) {
            is Action.StartGameClicked -> RoundInfoState(NIGHT)
            else -> throw IllegalStateException("Invalid action: $action passed to an Intro state")
        }
    }
}