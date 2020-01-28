package com.onewisebit.scpescape.fsm.states

import com.onewisebit.scpescape.fsm.GameMachine
import com.onewisebit.scpescape.fsm.Input
import com.onewisebit.scpescape.fsm.PlayersFilter
import com.onewisebit.scpescape.fsm.VoteJsonDataClass
import com.onewisebit.scpescape.fsm.actions.Action
import com.onewisebit.scpescape.model.entities.Participant

class VoteState : GameState {

    override suspend fun handleInput(gameMachine: GameMachine, action: Action, rules: Input) {
        if (rules is VoteJsonDataClass){
            val participants : List<Participant> = gameMachine.participants
            val candidatesList: HashSet<Long> = HashSet()
            val showList: HashSet<Long> = HashSet()
            val choiceList: HashSet<Long> = HashSet()

            //TODO: check what to do if rules.* is null
            candidatesList.addAll(filterPlayers(participants, gameMachine.currentParticipant.playerID, rules.show as PlayersFilter))
            showList.addAll(filterPlayers(participants, gameMachine.currentParticipant.playerID, rules.revealRole as PlayersFilter))
            choiceList.addAll(filterPlayers(participants, gameMachine.currentParticipant.playerID, rules.choiceEnabled as PlayersFilter))
        }
        else {
            throw IllegalArgumentException("A vote state was requested but the Input wasn't from a Vote Json")
        }
    }

    private fun filterPlayers(players : List<Participant>, currentParticipantId: Long, rules : PlayersFilter): HashSet<Long>{
        val result: HashSet<Long> = HashSet()
        if (rules.all == true)
            result.addAll(players.map { it.playerID })
        if (rules.self == true)
            result.add(currentParticipantId)
        else
            result.remove(currentParticipantId)
        if (!rules.role.isNullOrEmpty())
            result.addAll(players.filter {
                rules.role!!.contains(it.roleName)
            }.map { it.playerID })
        if (!rules.noRole.isNullOrEmpty())
            result.removeAll(players.filter {
                rules.noRole!!.contains(it.roleName)
            }.map { it.playerID })
        return result
    }

    override fun updateState() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private val TAG = VoteState::class.java.simpleName
    }
}