package com.onewisebit.scp_scarycontainmentpanic.model

import com.onewisebit.scp_scarycontainmentpanic.PlayersContract
import io.reactivex.Flowable

class PlayersModelImpl(playerRepository: PlayerRepository):PlayersContract.PlayersModel {

    private var repository:PlayerRepository = playerRepository

    override fun getAllPlayers(): Flowable<List<Player>> {
     return repository.getAllPlayers()
    }

    override fun getPlayersByName(name: String): Flowable<List<Player>> {
        return repository.getPlayersByName(name)
    }

}