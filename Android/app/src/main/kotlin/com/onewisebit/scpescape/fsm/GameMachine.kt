package com.onewisebit.scpescape.fsm

import com.onewisebit.scpescape.model.entities.Participant
import com.onewisebit.scpescape.model.entities.Player
import com.onewisebit.scpescape.model.entities.Round
import com.onewisebit.scpescape.model.entities.Turn
import io.reactivex.Flowable
import io.reactivex.Single

class GameMachine(gameStatePresenter: GameStateContract.GameStatePresenter) {
    val presenter: GameStateContract.GameStatePresenter = gameStatePresenter

    val participants: Single<List<Participant>>
    val players: Single<List<Player>>
    val rounds: Flowable<List<Round>>
    val turns: Flowable<List<Turn>>

    init {
        participants= presenter.getParticipants()
        players= presenter.getPlayers()
        rounds = presenter.getRounds()
        turns= presenter.getTurns()
    }


}