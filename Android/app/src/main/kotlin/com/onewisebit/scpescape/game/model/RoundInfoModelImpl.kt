package com.onewisebit.scpescape.game.model

import com.onewisebit.scpescape.game.RoundInfoContract
import com.onewisebit.scpescape.game.basemvp.ContractRound
import com.onewisebit.scpescape.model.repositories.InRoundRepository

class RoundInfoModelImpl(val roundRepository: InRoundRepository, val roundModel:ContractRound.ModelRound): RoundInfoContract.RoundInfoModel, ContractRound.ModelRound by roundModel {
}