package com.onewisebit.scpescape.fsm.states

import com.onewisebit.scpescape.fsm.actions.Action

class CheckVictoryState : StateGame {
    override fun consumeAction(action: Action): StateGame {
        return when (action) {
            is Action.VictoryReached -> EndGameState()
            //todo: these actions are already used, maybe change them? see [fsm.puml]
            is Action.StartNextRoundClicked -> RoundInfoState()
            else -> throw IllegalGameStateTransition("Invalid action: $action passed to a PassDevice state")
        }
    }
}