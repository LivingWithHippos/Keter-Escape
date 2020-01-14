package com.onewisebit.scpescape.fsm

import com.onewisebit.scpescape.model.entities.*

class GameStatePresenterImpl (gsView: GameStateContract.GameStateView,
                              gsModel: GameStateContract.GameStateModel
): GameStateContract.GameStatePresenter {

    private var view: GameStateContract.GameStateView = gsView
    private var model: GameStateContract.GameStateModel = gsModel

    override fun setGame(gameID: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getGame(): Game {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getParticipants(): List<Participant> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPlayers(): List<Player> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getRounds(): List<Round> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTurns(): List<Turn> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMode(): Mode {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}