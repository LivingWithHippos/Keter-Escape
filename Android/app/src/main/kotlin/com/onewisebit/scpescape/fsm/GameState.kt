package com.onewisebit.scpescape.fsm

interface GameState {

    suspend fun handleInput(gameMachine: GameMachine, action: Action, rules: Input)
    fun updateState()
}