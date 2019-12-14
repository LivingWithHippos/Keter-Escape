package com.onewisebit.scpescape.game.presenter

import com.onewisebit.scpescape.game.GameContract
import com.onewisebit.scpescape.model.GameInfo
import com.onewisebit.scpescape.model.entities.*
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Function5

class GamePresenterImpl(
    gView: GameContract.GameView,
    gModel: GameContract.GameModel
): GameContract.GamePresenter {

    private val model = gModel
    private val view = gView

    override fun setupGame(gameID: Long) {

        val game: Single<Game> = model.getGame(gameID)
        val mode: Single<Mode> = model.getMode(gameID)
        val participants: Flowable<List<Participant>> = model.getParticipant(gameID)
        val players: Flowable<List<Player>> = model.getPlayers(gameID)
        val roles: Flowable<List<Role>> = model.getRoles(gameID)
    }

}