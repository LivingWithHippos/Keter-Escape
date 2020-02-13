package com.onewisebit.scpescape.model.parsed

import com.onewisebit.scpescape.list.RecyclerItem
import com.onewisebit.scpescape.model.entities.Participant
import com.onewisebit.scpescape.model.entities.Player
import com.onewisebit.scpescape.utilities.TYPE_VOTE

data class VoteParticipant (
    val participant: Participant,
    val playerName: String,
    var show: Boolean = false,
    var revealRole: Boolean = false,
    var revealVote: Boolean = false,
    var votedPlayers: List<Player>? = null,
    var enabledVote: Boolean = false,
    override val id: String? = participant.playerID.toString(),
    override val type: Int = TYPE_VOTE
): RecyclerItem {

    override fun equals(other: Any?): Boolean {
        return (other is VoteParticipant) &&
                (participant.roleName == other.participant.roleName) &&
                (playerName == other.playerName)
    }
}