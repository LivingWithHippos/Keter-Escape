package com.onewisebit.scpescape.fsm.states

import com.onewisebit.scpescape.fsm.actions.Action

class DayNightState: StateGame {
    override suspend fun consumeAction(action: Action): StateGame {
        return when (action) {
            is Action.StartDayClicked -> PassDeviceState()
            else -> throw IllegalStateException("Invalid action: $action passed to a DayNight state")
        }
    }
}