package com.onewisebit.scpescape.fsm.states

import com.onewisebit.scpescape.fsm.actions.Action

class PassDeviceState : StateGame {
    override fun consumeAction(action: Action): StateGame {
        return when (action) {
            is Action.PassedToPlayerClicked -> PlayerTurnState()
            else -> throw IllegalGameStateTransition("Invalid action: $action passed to a PassDevice state")
        }
    }
}