package com.onewisebit.scpescape.game.view

import com.onewisebit.scpescape.fsm.actions.Action

//TODO: rename this to RoundSwitchFragment. This fragment will be shown between rounds an will describe what is going to happen in the next round. Also rename the other day/light pieces.
class DayNightFragment(gameID: Long, onActionListener: (action: Action) -> Unit) : BaseGameFragment(gameID, onActionListener), {
}