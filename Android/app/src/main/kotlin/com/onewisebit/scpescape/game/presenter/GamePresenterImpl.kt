package com.onewisebit.scpescape.game.presenter

import com.onewisebit.scpescape.game.GameContract
import com.onewisebit.scpescape.game.basemvp.ContractParticipant
import com.onewisebit.scpescape.game.basemvp.ContractRound
import com.onewisebit.scpescape.game.basemvp.ContractTurn
import com.onewisebit.scpescape.model.entities.*
import com.onewisebit.scpescape.model.parsed.ModeDataClass
import com.onewisebit.scpescape.utilities.PARTICIPANT_STATE_ALIVE

open class GamePresenterImpl(
    val gameView: GameContract.GameView,
    val gameModel: GameContract.GameModel,
    val roundPresenter: ContractRound.PresenterRound,
    val turnPresenter : ContractTurn.PresenterTurn,
    val participantPresenter : ContractParticipant.PresenterParticipant,
    val gameID: Long
) : GameContract.GamePresenter,
    ContractRound.PresenterRound by roundPresenter,
    ContractTurn.PresenterTurn by turnPresenter,
    ContractParticipant.PresenterParticipant by participantPresenter {

    override fun onDestroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun newPlayerTurn(): Int {
        // if this is null either the game is finished and no more turn should have been created
        // or there was a problem retrieving participants
        val missingParticipants : List<Long> = gameModel.getMissingTurnsParticipants(gameID)!!
        // this is supposing the round is created before the turns
        return gameModel.addTurn(gameID,missingParticipants.random())
    }
}