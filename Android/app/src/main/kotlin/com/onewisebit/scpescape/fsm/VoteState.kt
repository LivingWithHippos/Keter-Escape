package com.onewisebit.scpescape.fsm

import android.annotation.SuppressLint
import com.onewisebit.scpescape.model.entities.Participant

@SuppressLint("CheckResult")
class VoteState : GameState {

    override suspend fun handleInput(gameMachine: GameMachine, rules: Input) {
        if (rules is VoteJsonDataClass){
            val participants : List<Participant> = gameMachine.participants
            val candidatesList: HashSet<Long> = HashSet()
            val showList: HashSet<Long> = HashSet()
            val choiceList: HashSet<Long> = HashSet()

            val showParameters = rules["show"]
            candidatesList.addAll(filterPlayers(participants, gameMachine.currentParticipant.playerID, showParameters))

            val revealParameters = rules["reveal_role"]
            showList.addAll(filterPlayers(participants, gameMachine.currentParticipant.playerID, revealParameters))

            val choiceParameters = rules["choice_enabled"]
            choiceList.addAll(filterPlayers(participants, gameMachine.currentParticipant.playerID, choiceParameters))
        }
        else {
            //TODO: add exception for unrecognized rules, add check for extends = vote
        }

    }

    private fun filterPlayers(players : List<Participant>, currentParticipantId: Long, rules : Parameter?): HashSet<Long>{
        val result: HashSet<Long> = HashSet()
        if (rules?.parameters?.firstOrNull { it.name == "all" }?.getSingleBoolean() == true)
            result.addAll(players.map { it.playerID })
        if (rules?.parameters?.firstOrNull { it.name == "self" }?.getSingleBoolean() == true)
            result.add(currentParticipantId)
        else
            result.remove(currentParticipantId)
        if (!rules?.parameters?.firstOrNull { it.name == "role" }?.getStringList().isNullOrEmpty())
            result.addAll(players.filter {
                rules?.parameters?.firstOrNull { role -> role.name == "role" }?.getStringList()?.contains(it.roleName) == true
            }.map { it.playerID })
        if (!rules?.parameters?.firstOrNull { it.name == "no_role" }?.getStringList().isNullOrEmpty())
            result.removeAll(players.filter {
                rules?.parameters?.firstOrNull { role -> role.name == "no_role" }?.getStringList()?.contains(it.roleName) == true
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