package com.onewisebit.scpescape.fsm

import com.onewisebit.scpescape.model.entities.*

interface GameStateContract {

    interface GameStateView

    interface GameStatePresenter {
        fun setGame(gameID: Long)
        fun getGame(): Game
        fun getParticipants(): List<Participant>
        fun getPlayers(): List<Player>
        fun getRounds(): List<Round>
        fun getTurns(): List<Turn>
        fun getMode(): Mode
    }

    interface GameStateModel {

        fun getGame(gameID: Long): Game
        fun getParticipants(gameID: Long): List<Participant>
        fun getPlayers(gameID: Long): List<Player>
        fun getRounds(gameID: Long): List<Round>
        fun getTurns(gameID: Long): List<Turn>
        fun getMode(gameID: Long): Mode

    }
}