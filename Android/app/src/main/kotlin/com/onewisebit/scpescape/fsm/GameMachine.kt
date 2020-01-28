package com.onewisebit.scpescape.fsm

import com.onewisebit.scpescape.game.GameStateContract
import com.onewisebit.scpescape.model.entities.Participant
import com.onewisebit.scpescape.model.entities.Player
import com.onewisebit.scpescape.model.entities.Round
import com.onewisebit.scpescape.model.entities.Turn
import io.reactivex.Flowable
import io.reactivex.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

//TODO: just use presenter this clas is not really necessary
class GameMachine(gameStatePresenter: GameStateContract.GameStatePresenter) {
    val presenter: GameStateContract.GameStatePresenter = gameStatePresenter

    lateinit var participants: List<Participant>
    lateinit var  players: Single<List<Player>>
    lateinit var  rounds: Flowable<List<Round>>
    lateinit var  turns: Flowable<List<Turn>>
    lateinit var  currentParticipant: Participant

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    init {
        uiScope.launch {
            participants = presenter.getParticipants()
            players = presenter.getPlayers()
            rounds = presenter.getRounds()
            turns = presenter.getTurns()
            currentParticipant = presenter.getCurrentParticipant()
        }
    }


    fun onDestroy(){
        job.cancel()
    }
}