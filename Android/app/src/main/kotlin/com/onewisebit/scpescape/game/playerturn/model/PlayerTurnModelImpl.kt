package com.onewisebit.scpescape.game.playerturn.model

import com.onewisebit.scpescape.game.playerturn.PlayerTurnContract
import com.onewisebit.scpescape.game.composable.ContractParticipant
import com.onewisebit.scpescape.game.composable.ContractPlayer

class PlayerTurnModelImpl(
    val playerModel: ContractPlayer.ModelPlayer,
    val participantModel: ContractParticipant.ModelParticipant
) :
    ContractPlayer.ModelPlayer by playerModel,
    ContractParticipant.ModelParticipant by participantModel,
    PlayerTurnContract.PlayerTurnModel