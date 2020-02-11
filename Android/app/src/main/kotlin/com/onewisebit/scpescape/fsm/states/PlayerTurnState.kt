package com.onewisebit.scpescape.fsm.states

import com.onewisebit.scpescape.fsm.actions.Action

class PlayerTurnState : StateGame {
    override fun consumeAction(action: Action): StateGame {
        return when (action) {
            is Action.EndRoundClicked -> ShowResultsState()
            is Action.EndTurnClicked -> PassDeviceState()
            is Action.StartVotePowerClicked -> VoteState()
            is Action.PlayTurnClicked -> VoteState()
            else -> throw IllegalStateException("Invalid action: $action passed to a PlayerTurn state")
        }
    }
}