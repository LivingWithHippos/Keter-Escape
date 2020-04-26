package com.onewisebit.scpescape.fsm.actions

sealed class Action {
    class EndTurnClicked : Action()
    class EndGameClicked : Action()
    class PassedToPlayerClicked : Action()
    class PlayTurnClicked : Action()
    class VoteCasted : Action()
    class ResultSeenClicked : Action()
    class EndRoundClicked : Action()
    class StartVotePowerClicked : Action()
    class StartInfoPowerClicked : Action()
    class VictoryReached : Action()
    class StartDayRoundClicked : Action()
    class StartNightRoundClicked : Action()
    class StartGameClicked : Action()
}