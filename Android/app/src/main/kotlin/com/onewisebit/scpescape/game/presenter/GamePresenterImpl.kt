package com.onewisebit.scpescape.game.presenter

import com.onewisebit.scpescape.game.GameContract
import com.onewisebit.scpescape.game.basemvp.ContractRound
import com.onewisebit.scpescape.model.entities.*
import com.onewisebit.scpescape.model.parsed.ModeDataClass
import com.onewisebit.scpescape.utilities.PARTICIPANT_STATE_ALIVE

open class GamePresenterImpl(
    val gameView: GameContract.GameView,
    val gameModel: GameContract.GameModel,
    val roundPresenter: ContractRound.PresenterRound,
    val gameID: Long
) : GameContract.GamePresenter, ContractRound.PresenterRound by roundPresenter {

    override fun onDestroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}