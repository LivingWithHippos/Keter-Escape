package com.onewisebit.scpescape.fsm.states

import com.onewisebit.scpescape.fsm.actions.Action

class StateVote: StateGame {

    override fun consumeAction(action: Action): StateGame {
        return when(action){
            is Action.EndRoundClicked -> ShowResultsState()
            is Action.EndTurnClicked -> PassDeviceState()
            else -> throw IllegalStateException("Invalid action: $action passed to a Vote state")
        }
    }
}