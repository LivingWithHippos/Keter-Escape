package com.onewisebit.scpescape.model.parsed

import com.onewisebit.scpescape.model.entities.Participant
import com.onewisebit.scpescape.model.entities.Player

data class VoteParticipant(
    val participant : Participant,
    val playerName : String,
    var show : Boolean = false,
    var revealRole : Boolean = false,
    var revealVote : Boolean = false,
    var votedPlayers : List<Player>? = null,
    var enabledVote : Boolean = false
){}