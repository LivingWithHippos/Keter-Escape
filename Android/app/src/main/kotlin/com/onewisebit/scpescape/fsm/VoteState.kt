package com.onewisebit.scpescape.fsm

import android.annotation.SuppressLint
import com.onewisebit.scpescape.model.entities.Participant

@SuppressLint("CheckResult")
class VoteState : GameState {

    override suspend fun handleInput(gameMachine: GameMachine, rules: HashMap<String, HashMap<String, Parameter>>) {
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

    private fun filterPlayers(players : List<Participant>, currentParticipantId: Long, rules : HashMap<String, Parameter>?): HashSet<Long>{
        val result: HashSet<Long> = HashSet()
        if (rules?.get("all")?.getSingleBoolean() == true)
            result.addAll(players.map { it.playerID })
        if (rules?.get("self")?.getSingleBoolean() == true)
            result.add(currentParticipantId)
        else
            result.remove(currentParticipantId)
        if (!rules?.get("role")?.getStringList().isNullOrEmpty())
            result.addAll(players.filter {
                rules?.get("role")?.getStringList()?.contains(it.roleName) == true
            }.map { it.playerID })
        if (!rules?.get("no_role")?.getStringList().isNullOrEmpty())
            result.removeAll(players.filter {
                rules?.get("no_role")?.getStringList()?.contains(it.roleName) == true
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