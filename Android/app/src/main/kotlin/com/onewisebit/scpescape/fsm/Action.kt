package com.onewisebit.scpescape.fsm

sealed class Action {
    class EndTurnClicked : Action()
    class PassedToPlayerClicked : Action()
    class StartDayClicked : Action()
    class StartNightClicked : Action()
    class VoteCasted : Action()
    class ResultSeenClicked : Action()
}