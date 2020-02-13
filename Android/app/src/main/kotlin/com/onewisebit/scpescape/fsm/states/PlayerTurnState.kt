package com.onewisebit.scpescape.fsm.states

import com.onewisebit.scpescape.fsm.actions.Action

class PlayerTurnState : StateGame {
    override fun consumeAction(action: Action): StateGame {
        return when (action) {
            is Action.EndRoundClicked -> ShowResultsState()
            is Action.EndTurnClicked -> PassDeviceState()
            is Action.StartVotePowerClicked -> TurnVoteState()
            is Action.StartInfoPowerClicked -> TurnInfoState()
            is Action.PlayTurnClicked -> PlayerPowerState()
            //todo: replace all these with illegal argument?
            else -> throw IllegalStateException("Invalid action: $action passed to a PlayerTurn state")
        }
    }
}