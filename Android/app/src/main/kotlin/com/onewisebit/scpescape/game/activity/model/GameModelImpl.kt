package com.onewisebit.scpescape.game.activity.model

import com.onewisebit.scpescape.game.activity.GameContract
import com.onewisebit.scpescape.game.composable.*

open class GameModelImpl(
    val gameModel: ContractGame.ModelGame,
    val roundModel: ContractRound.ModelRound,
    val turnModel: ContractTurn.ModelTurn,
    val participantModel: ContractParticipant.ModelParticipant,
    val playerModel: ContractPlayer.ModelPlayer,
    val actionModel: ContractAction.ModelAction,
    val voteModel: ContractVote.ModelVote
) : GameContract.GameModel,
    ContractGame.ModelGame by gameModel,
    ContractRound.ModelRound by roundModel,
    ContractTurn.ModelTurn by turnModel,
    ContractParticipant.ModelParticipant by participantModel,
    ContractPlayer.ModelPlayer by playerModel,
    ContractAction.ModelAction by actionModel,
    ContractVote.ModelVote by voteModel