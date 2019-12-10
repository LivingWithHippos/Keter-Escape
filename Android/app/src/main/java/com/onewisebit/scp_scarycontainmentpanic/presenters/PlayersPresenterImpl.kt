package com.onewisebit.scp_scarycontainmentpanic.presenters

import com.onewisebit.scp_scarycontainmentpanic.PlayersContract
import com.onewisebit.scp_scarycontainmentpanic.model.Participant
import io.reactivex.Flowable
import io.reactivex.Single

class PlayersPresenterImpl(
    pView: PlayersContract.PlayersView,
    pModel: PlayersContract.PlayersModel
) : PlayersContract.PlayersPresenter {

    private var view: PlayersContract.PlayersView = pView
    private var model: PlayersContract.PlayersModel = pModel

    override fun setPlayers(gameID: Long) {
        view.initView(model.getAllPlayers(), model.getParticipantsIDByGame(gameID))
    }

    override fun addParticipant(gameID: Long, playerID: Long) =
        model.addGameParticipant(gameID, playerID)

    override fun removeParticipant(gameID: Long, playerID: Long) =
        model.removeGameParticipant(gameID, playerID)

    override fun getParticipants(gameID: Long): Flowable<List<Participant>> =
        model.getParticipantsByGame(gameID)

    override fun getParticipantsNumber(gameID: Long): Single<Int> {
        return model.getParticipantsNumber(gameID)
    }

}