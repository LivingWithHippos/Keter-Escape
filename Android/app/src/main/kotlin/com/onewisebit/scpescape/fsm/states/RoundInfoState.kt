package com.onewisebit.scpescape.fsm.states

import com.onewisebit.scpescape.fsm.actions.Action

class RoundInfoState() : StateGame {
    override fun consumeAction(action: Action): StateGame {
        return when (action) {
            is Action.StartNextRoundClicked -> PassDeviceState()
            else -> throw IllegalGameStateTransition("Invalid action: $action passed to a RoundInfo state")
        }
    }
}