package com.onewisebit.scpescape.fsm

interface GameState {

    suspend fun handleInput(gameMachine: GameMachine, rules: Input)
    fun updateState()
}