package com.onewisebit.scpescape.game.model

import com.onewisebit.scpescape.game.GameContract
import com.onewisebit.scpescape.game.basemvp.ContractParticipant
import com.onewisebit.scpescape.game.basemvp.ContractPlayer
import com.onewisebit.scpescape.game.basemvp.ContractRound
import com.onewisebit.scpescape.game.basemvp.ContractTurn

open class GameModelImpl(
    val roundModel: ContractRound.ModelRound,
    val turnModel: ContractTurn.ModelTurn,
    val participantModel: ContractParticipant.ModelParticipant,
    val playerModel: ContractPlayer.ModelPlayer
) : GameContract.GameModel,
    ContractRound.ModelRound by roundModel,
    ContractTurn.ModelTurn by turnModel,
    ContractParticipant.ModelParticipant by participantModel,
    ContractPlayer.ModelPlayer by playerModel