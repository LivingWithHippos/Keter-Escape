package com.onewisebit.scpescape.fsm.states

import com.onewisebit.scpescape.fsm.actions.Action

class PlayerPowerState : StateGame {

    override fun consumeAction(action: Action): StateGame {
        return when (action) {
            is Action.EndRoundClicked -> ShowResultsState()
            is Action.EndTurnClicked -> PassDeviceState()
            else -> throw IllegalGameStateTransition("Invalid action: $action passed to a PlayerPower state")
        }
    }
}