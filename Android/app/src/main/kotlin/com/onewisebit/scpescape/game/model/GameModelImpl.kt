package com.onewisebit.scpescape.game.model

import com.onewisebit.scpescape.game.GameContract
import com.onewisebit.scpescape.game.composable.*

open class GameModelImpl(
    val roundModel: ContractRound.ModelRound,
    val turnModel: ContractTurn.ModelTurn,
    val participantModel: ContractParticipant.ModelParticipant,
    val playerModel: ContractPlayer.ModelPlayer,
    val actionModel: ContractAction.ModelAction
) : GameContract.GameModel,
    ContractRound.ModelRound by roundModel,
    ContractTurn.ModelTurn by turnModel,
    ContractParticipant.ModelParticipant by participantModel,
    ContractPlayer.ModelPlayer by playerModel,
    ContractAction.ModelAction by actionModel