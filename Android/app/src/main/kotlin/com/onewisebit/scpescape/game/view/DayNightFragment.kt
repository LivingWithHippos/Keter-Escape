package com.onewisebit.scpescape.game.view

import com.onewisebit.scpescape.fsm.actions.Action

class DayNightFragment(gameID: Long, private val onActionListener: (action: Action) -> Unit) : BaseGameFragment(gameID, onActionListener) {
}