package com.onewisebit.scpescape.game.vote.presenter

import com.onewisebit.scpescape.game.composable.ContractAction
import com.onewisebit.scpescape.game.composable.ContractParticipant
import com.onewisebit.scpescape.game.composable.ContractPlayer
import com.onewisebit.scpescape.game.composable.ContractVote
import com.onewisebit.scpescape.game.vote.VoteTurnContract
import com.onewisebit.scpescape.model.entities.Participant
import com.onewisebit.scpescape.model.entities.Player
import com.onewisebit.scpescape.model.entities.Vote
import com.onewisebit.scpescape.model.parsed.PlayerFilter
import com.onewisebit.scpescape.model.parsed.VoteParticipant
import com.onewisebit.scpescape.model.parsed.VoteSettings

class VoteTurnPresenterImpl(
    val view: VoteTurnContract.VoteTurnView,
    val votePresenter: ContractVote.PresenterVote,
    val actionPresenter: ContractAction.PresenterAction,
    val participantPresenter: ContractParticipant.PresenterParticipant,
    val playerPresenter: ContractPlayer.PresenterPlayer,
    val gameID: Long
) : VoteTurnContract.VoteTurnPresenter,
    ContractVote.PresenterVote by votePresenter,
    ContractParticipant.PresenterParticipant by participantPresenter,
    ContractAction.PresenterAction by actionPresenter,
    ContractPlayer.PresenterPlayer by playerPresenter {

    private var action: VoteSettings? = null

    override suspend fun loadValues(roleName: String, roundCode: String) {

        // list of roles to be shown, can be null, base on "reveal_role" from vote.json
        val participants: List<Participant> = participantPresenter.getAliveParticipants()
        val currentParticipant: Participant = participantPresenter.getCurrentParticipant()
        // list of players to be shown, cannot be null, base on "show" from vote.json
        val players: List<Player> = playerPresenter.getPlayers(participants)
        // list of votes to be shown, can be null, base on "reveal_vote" from vote.json
        val votes: List<Vote> = votePresenter.getLastRoundVotes()
        // get the current player action. it's been merged with the template so no values should be null
        getAction(roleName, roundCode).run {
            if (this is VoteSettings) {
                action = this
                val pVoteList: MutableList<VoteParticipant> = mutableListOf()

                participants.forEach {
                    val vp: VoteParticipant =
                        VoteParticipant(it, players.first { pl -> pl.id == it.playerID }.name)
                    vp.show = applySettings(it, currentParticipant, this.show!!)
                    // shows only needed players
                    if (vp.show) {

                        vp.revealRole = applySettings(it, currentParticipant, this.revealRole!!)
                        vp.revealVote = applySettings(it, currentParticipant, this.revealVote!!)
                        if (vp.revealVote) {

                            val votedPlayers: List<Long> = votes
                                .filter { voter -> voter.playerID == it.playerID }
                                .map { voter -> voter.votedPlayerID }

                            vp.votedPlayers = players
                                .filter { player -> votedPlayers.contains(player.id) }

                        }
                        vp.enabledVote = applySettings(it, currentParticipant, this.choiceEnabled!!)

                        pVoteList.add(vp)
                    }
                }

                view.initializeList(pVoteList)
                // useful for loading turns
                checkVotes()
            } else
                throw IllegalArgumentException("Wrong action loaded in turn fragment for game $gameID, round $roundCode, role $roleName")
        }

    }

    override suspend fun setCurrentTurnVote(votedPlayerId: Long): Boolean {
        var inserted = false
        //check if the vote can be added
        if (action != null) {
            val votes = getCurrentPlayerVotes()
            // I'm removing a vote
            if (votes.map { it.votedPlayerID }.contains(votedPlayerId)) {
                removeCurrentTurnVote(votedPlayerId)
            }
            // I`m adding a vote
            else {
                //controlla choice number sulle impostazioni
                // se "exactly": vedi se è già così
                //se "range": [-1,-1], vedi se è al max
                action!!.choiceNumber?.let {
                    // has the player already reached the maximum number of votes?
                    if ((it.exactly!! > 0 &&
                                votes.size < it.exactly!!) ||
                        (it.range!![1] > 0 &&
                                votes.size < it.range!![1])
                    ) {
                        addCurrentRoundVote(votedPlayerId, action!!.name)
                        inserted = true
                    }
                }
            }
        } else
        //todo: optionally add a method to model to get current turn action
            throw NullActionException("Stored action was null.")

        return inserted
    }

    private fun applySettings(
        participant: Participant,
        currentParticipant: Participant,
        settings: PlayerFilter
    ): Boolean {
        var result = false

        // we check from the less specific to the most specific condition
        if (settings.all == true)
            result = true

        if (!settings.role.isNullOrEmpty())
            if (settings.role!!.contains(participant.roleName))
                result = true

        if (!settings.noRole.isNullOrEmpty())
            if (settings.noRole!!.contains(participant.roleName))
                result = false

        if (participant.playerID == currentParticipant.playerID)
            result = (settings.self == true)

        return result
    }

    override suspend fun checkVotes() {
        action?.let {

            val settings = it.choiceNumber!!
            var enableFab = false
            val votes = getCurrentPlayerVotes()

            if (settings.exactly!! > 0)
                if (settings.exactly!! == votes.size)
                    enableFab = true

            if (settings.range!![1] > 0)
                if (votes.size >= settings.range!![0] &&
                    votes.size <= settings.range!![1]
                )
                    enableFab = true


            if (settings.zeroAllowed!!)
                if (votes.isEmpty())
                    enableFab = true

            view.setFab(visible = enableFab)
        }
    }
}

class NullActionException(message: String) : RuntimeException(message)