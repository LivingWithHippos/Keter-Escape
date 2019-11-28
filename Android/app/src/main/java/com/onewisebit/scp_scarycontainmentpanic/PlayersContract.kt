package com.onewisebit.scp_scarycontainmentpanic

import com.onewisebit.scp_scarycontainmentpanic.model.Player
import io.reactivex.Flowable

interface PlayersContract {

    interface PlayersView {
        fun initView(players: ArrayList<Player>)
    }

    interface PlayersPresenter {
    }

    interface PlayersModel {
        fun getAllPlayers(): Flowable<List<Player>>
    }
}