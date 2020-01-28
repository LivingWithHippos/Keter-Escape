package com.onewisebit.scpescape.fsm.states

import com.onewisebit.scpescape.fsm.actions.Action

interface StateGame {
    suspend fun consumeAction(action: Action): StateGame
}