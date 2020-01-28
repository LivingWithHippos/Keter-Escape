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
            //TODO: check what to do if null
            val showParameters = rules.show
            candidatesList.addAll(filterPlayers(participants, gameMachine.currentParticipant.playerID, showParameters as PlayersFilter))

            val revealParameters = rules.revealRole
            showList.addAll(filterPlayers(participants, gameMachine.currentParticipant.playerID, revealParameters as PlayersFilter))

            val choiceParameters = rules.choiceEnabled
            choiceList.addAll(filterPlayers(participants, gameMachine.currentParticipant.playerID, choiceParameters as PlayersFilter))
        }
        else {
            //TODO: add exception for unrecognized rules, add check for extends = vote
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