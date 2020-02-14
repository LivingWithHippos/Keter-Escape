package com.onewisebit.scpescape.game.vote.presenter

import com.onewisebit.scpescape.game.composable.ContractAction
import com.onewisebit.scpescape.game.composable.ContractParticipant
import com.onewisebit.scpescape.game.composable.ContractPlayer
import com.onewisebit.scpescape.game.composable.ContractVote
import com.onewisebit.scpescape.game.vote.VoteContract
import com.onewisebit.scpescape.model.entities.Participant
import com.onewisebit.scpescape.model.entities.Player
import com.onewisebit.scpescape.model.entities.Vote
import com.onewisebit.scpescape.model.parsed.PlayerFilter
import com.onewisebit.scpescape.model.parsed.VoteParticipant
import com.onewisebit.scpescape.model.parsed.VoteSettings

class VotePresenterImpl(
    val view: VoteContract.VoteView,
    val votePresenter: ContractVote.PresenterVote,
    val actionPresenter: ContractAction.PresenterAction,
    val participantPresenter: ContractParticipant.PresenterParticipant,
    val playerPresenter: ContractPlayer.PresenterPlayer,
    val gameID: Long
) : VoteContract.VotePresenter,
    ContractVote.PresenterVote by votePresenter,
    ContractParticipant.PresenterParticipant by participantPresenter,
    ContractAction.PresenterAction by actionPresenter,
    ContractPlayer.PresenterPlayer by playerPresenter {

    override suspend fun loadValues(roleName: String, roundCode: String) {

        // list of roles to be shown, can be null, base on "reveal_role" from vote.json
        val participants: List<Participant> = participantPresenter.getAliveParticipants()
        val currentParticipant: Participant = participantPresenter.getCurrentParticipant()
        // list of players to be shown, cannot be null, base on "show" from vote.json
        val players: List<Player> = playerPresenter.getPlayers(participants)
        // list of votes to be shown, can be null, base on "reveal_vote" from vote.json
        val votes: List<Vote> = votePresenter.getLastRoundVotes()
        // get the current player action
        val action = getAction(roleName, roundCode)

        val pVoteList: MutableList<VoteParticipant> = mutableListOf()

        if (action is VoteSettings) {
            participants.forEach {
                val vp: VoteParticipant =
                    VoteParticipant(it, players.first { pl -> pl.id == it.playerID }.name)
                vp.show = applySettings(it, currentParticipant, action.show!!)
                // shows only needed players
                if (vp.show) {

                    vp.revealRole = applySettings(it, currentParticipant, action.revealRole!!)
                    vp.revealVote = applySettings(it, currentParticipant, action.revealVote!!)
                    if (vp.revealVote) {

                        val votedPlayers: List<Long> = votes
                            .filter { voter -> voter.playerID == it.playerID }
                            .map { voter -> voter.votedPlayerID }

                        vp.votedPlayers = players
                            .filter { player -> votedPlayers.contains(player.id) }

                    }
                    vp.enabledVote = applySettings(it, currentParticipant, action.choiceEnabled!!)

                    pVoteList.add(vp)
                }
            }

            view.initializeList(pVoteList)
        } else throw IllegalArgumentException("Wrong action loaded in turn fragment for game $gameID, round $roundCode, role $roleName")

    }

    private fun applySettings(
        participant: Participant,
        currentParticipant: Participant,
        settings: PlayerFilter
    ): Boolean {
        var result = false

        if (settings.all == true)
            result = true

        if (!settings.role.isNullOrEmpty())
            if (settings.role!!.contains(participant.roleName))
                result = true

        if (!settings.noRole.isNullOrEmpty())
            if (settings.noRole!!.contains(participant.roleName))
                result = false

        if (settings.self == true)
            if (participant.playerID == currentParticipant.playerID)
                result = true

        return result
    }
}