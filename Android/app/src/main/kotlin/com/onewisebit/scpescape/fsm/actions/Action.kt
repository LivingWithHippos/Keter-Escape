package com.onewisebit.scpescape.fsm.actions

sealed class Action {
    class EndTurnClicked : Action()
    class PassedToPlayerClicked : Action()
    class PlayTurnClicked : Action()
    class VoteCasted : Action()
    class ResultSeenClicked : Action()
    class EndRoundClicked : Action()
    class StartVotePowerClicked : Action()
    class StartInfoPowerClicked : Action()
    class StartRoundClicked : Action()
    class StartGameClicked : Action()
}