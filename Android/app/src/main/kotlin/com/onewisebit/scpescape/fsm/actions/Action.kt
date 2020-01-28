package com.onewisebit.scpescape.fsm.actions

sealed class Action {
    class EndTurnClicked : Action()
    class PassedToPlayerClicked : Action()
    class StartDayClicked : Action()
    class StartNightClicked : Action()
    class VoteCasted : Action()
    class ResultSeenClicked : Action()
}