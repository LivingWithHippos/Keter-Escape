package com.onewisebit.scpescape.fsm.states

import com.onewisebit.scpescape.fsm.actions.Action

class ShowResultsState : StateGame {
    override fun consumeAction(action: Action): StateGame {
        return when (action) {
            is Action.StartRoundClicked -> RoundInfoState()
            else -> throw IllegalGameStateTransition("Invalid action: $action passed to a ShowResults state")
        }
    }
}