package com.onewisebit.scpescape.fsm.states

import com.onewisebit.scpescape.fsm.actions.Action
import com.onewisebit.scpescape.utilities.NIGHT

class EndGameState : StateGame {
    override fun consumeAction(action: Action): StateGame {
        return when (action) {
            is Action.EndGameClicked -> CloseGameState()
            else -> throw IllegalGameStateTransition("Invalid action: $action passed to an EndGame state")
        }
    }
}