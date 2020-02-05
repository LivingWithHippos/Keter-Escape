package com.onewisebit.scpescape.game.model

import com.onewisebit.scpescape.game.GameContract
import com.onewisebit.scpescape.game.basemvp.ContractRound
import com.onewisebit.scpescape.model.entities.*
import com.onewisebit.scpescape.model.parsed.ModeDataClass
import com.onewisebit.scpescape.model.repositories.*

open class GameModelImpl(
    val roundModel: ContractRound.ModelRound
) : GameContract.GameModel,
ContractRound.ModelRound by roundModel {
}