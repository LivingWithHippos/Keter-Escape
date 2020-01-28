package com.onewisebit.scpescape.fsm.states

import com.onewisebit.scpescape.fsm.GameMachine
import com.onewisebit.scpescape.fsm.Input
import com.onewisebit.scpescape.fsm.actions.Action

interface GameState {

    suspend fun handleInput(gameMachine: GameMachine, action: Action, rules: Input)
    fun updateState()
}