package com.onewisebit.scpescape.fsm

import com.onewisebit.scpescape.model.entities.Player

class GameMachine(_players: List<Player> = emptyList()) {
    var players: List<Player> = _players
    var currentPlayer : Player? = null
}