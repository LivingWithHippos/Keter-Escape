package com.onewisebit.scpescape.fsm

interface GameState {

    suspend fun handleInput(gameMachine: GameMachine, rules: HashMap<String, HashMap<String, Parameter>>)
    fun updateState()
}