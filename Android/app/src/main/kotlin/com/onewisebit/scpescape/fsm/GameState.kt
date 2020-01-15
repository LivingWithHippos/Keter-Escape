package com.onewisebit.scpescape.fsm

interface GameState {

    fun handleInput(gameMachine: GameMachine, rules: HashMap<String, List<Parameter>>)
    fun updateState()
}