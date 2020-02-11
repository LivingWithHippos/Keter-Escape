package com.onewisebit.scpescape.game.roundinfo.model

import com.onewisebit.scpescape.game.roundinfo.RoundInfoContract
import com.onewisebit.scpescape.game.composable.ContractRound
import com.onewisebit.scpescape.model.repositories.InRoundRepository

class RoundInfoModelImpl(
    val roundRepository: InRoundRepository,
    val roundModel: ContractRound.ModelRound
) :
    RoundInfoContract.RoundInfoModel,
    ContractRound.ModelRound by roundModel