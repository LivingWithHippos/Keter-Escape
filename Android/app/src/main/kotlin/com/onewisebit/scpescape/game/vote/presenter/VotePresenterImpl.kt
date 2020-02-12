package com.onewisebit.scpescape.game.vote.presenter

import com.onewisebit.scpescape.game.vote.VoteContract
import com.onewisebit.scpescape.game.composable.ContractAction
import com.onewisebit.scpescape.game.composable.ContractParticipant
import com.onewisebit.scpescape.game.composable.ContractVote
import com.onewisebit.scpescape.model.entities.Participant
import com.onewisebit.scpescape.model.entities.Player
import com.onewisebit.scpescape.model.entities.Vote
import com.onewisebit.scpescape.model.parsed.TurnAction
import com.onewisebit.scpescape.model.parsed.VoteTurn

class VotePresenterImpl(
    val view: VoteContract.VoteView,
    val votePresenter:ContractVote.PresenterVote,
    val actionPresenter:ContractAction.PresenterAction,
    val participantPresenter:ContractParticipant.PresenterParticipant,
    val gameID: Long,
    val roundCode : String,
    val roleName: String
): VoteContract.VotePresenter,
    ContractVote.PresenterVote by votePresenter,
    ContractParticipant.PresenterParticipant by participantPresenter,
    ContractAction.PresenterAction by actionPresenter {

    override suspend fun loadValues() {

        // list of players to be shown, cannot be null, base on "show" from vote.json
        var players: List<Player> =
        // list of roles to be shown, can be null, base on "reveal_role" from vote.json
        var participants: List<Participant> = participantPresenter.getAliveParticipants()
        // list of votes to be shown, can be null, base on "reveal_vote" from vote.json
        var votes: List<Vote> = votesList
        // list of players voted, must correspond to player voted in vote list
        var votedPlayers: List<Player> = votedPlayersList
        // list of player id that can be selected, can be null, based on "choice_enabled" from vote.json
        var enabledPlayers: List<Long> = enabledPlayersList

        val action = getAction(roleName,roundCode)
        if (action is VoteTurn)
        {
           // action.
        }
        else throw IllegalArgumentException("Wrong action loaded in turn fragment for game $gameID, round $roundCode, role $roleName")

        //view.updateList()
    }
}