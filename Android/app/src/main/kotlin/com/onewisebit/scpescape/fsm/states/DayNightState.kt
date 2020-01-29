package com.onewisebit.scpescape.fsm.states

import com.onewisebit.scpescape.fsm.actions.Action

class DayNightState(val dayOrNight : Int): StateGame {
    override fun consumeAction(action: Action): StateGame {
        return when (action) {
            is Action.StartDayClicked -> PassDeviceState()
            else -> throw IllegalStateException("Invalid action: $action passed to a DayNight state")
        }
    }
}