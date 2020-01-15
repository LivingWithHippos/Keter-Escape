package com.onewisebit.scpescape.fsm

import com.onewisebit.scpescape.model.entities.Player

class VoteState: GameState {
    override fun handleInput(gameMachine: GameMachine, rules: HashMap<String, List<Parameter>>) {
        val candidates : HashSet<Player> = HashSet(gameMachine.players.size)
        rules.forEach { rule ->
            when(rule.key){
                "show" -> {
                    rule.value.forEach{ parameter ->
                        when (parameter.name) {
                            //TODO: check that the parameters are always ordered. For example using the "all" parameter as last would just re-add all the players
                            "all" -> {
                                if (parameter.getSingleBoolean())
                                    candidates.addAll(gameMachine.players)
                            }
                            "self" -> {
                                if (parameter.getSingleBoolean())
                                    if(gameMachine.currentPlayer != null)
                                        candidates.add(gameMachine.currentPlayer!!)
                                else
                                    if(gameMachine.currentPlayer != null)
                                        candidates.remove(gameMachine.currentPlayer!!)
                            }
                            "role" -> {

                            }

                        }
                    }
                }
            }
        }

    }

    override fun update(gameMachine: GameMachine) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}