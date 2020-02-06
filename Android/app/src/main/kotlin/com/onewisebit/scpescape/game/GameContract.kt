package com.onewisebit.scpescape.game

import com.onewisebit.scpescape.game.basemvp.ContractParticipant
import com.onewisebit.scpescape.game.basemvp.ContractPlayer
import com.onewisebit.scpescape.game.basemvp.ContractRound
import com.onewisebit.scpescape.game.basemvp.ContractTurn

/**
 * Generic contract with all the game data needed, is implemented by the game single contract
 */
interface GameContract {

    interface GameView

    interface GamePresenter: ContractRound.PresenterRound, ContractTurn.PresenterTurn, ContractParticipant.PresenterParticipant, ContractPlayer.PresenterPlayer {
        fun onDestroy()
        suspend fun newPlayerTurn(): String
    }

    interface GameModel : ContractRound.ModelRound, ContractTurn.ModelTurn, ContractParticipant.ModelParticipant, ContractPlayer.ModelPlayer {
    }
}