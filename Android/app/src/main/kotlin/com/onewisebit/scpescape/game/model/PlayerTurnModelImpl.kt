package com.onewisebit.scpescape.game.model

import com.onewisebit.scpescape.game.PlayerTurnContract
import com.onewisebit.scpescape.game.basemvp.ContractParticipant
import com.onewisebit.scpescape.game.basemvp.ContractPlayer

class PlayerTurnModelImpl (
    val playerModel: ContractPlayer.ModelPlayer,
    val participantModel: ContractParticipant.ModelParticipant
) :
    ContractPlayer.ModelPlayer by playerModel,
    ContractParticipant.ModelParticipant by participantModel,
    PlayerTurnContract.PlayerTurnModel {

}