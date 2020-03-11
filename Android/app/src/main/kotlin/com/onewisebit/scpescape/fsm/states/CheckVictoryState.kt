package com.onewisebit.scpescape.fsm.states

import com.onewisebit.scpescape.fsm.actions.Action
import com.onewisebit.scpescape.utilities.DAY
import com.onewisebit.scpescape.utilities.NIGHT

class CheckVictoryState : StateGame {
    override fun consumeAction(action: Action): StateGame {
        return when (action) {
            is Action.VictoryReached -> PlayerTurnState()
            is Action.StartDayRoundClicked -> RoundInfoState(DAY)
            is Action.StartNightRoundClicked -> RoundInfoState(NIGHT)
            else -> throw IllegalGameStateTransition("Invalid action: $action passed to a PassDevice state")
        }
    }
}