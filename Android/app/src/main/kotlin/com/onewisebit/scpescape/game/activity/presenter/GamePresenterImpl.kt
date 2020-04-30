package com.onewisebit.scpescape.game.activity.presenter

import com.onewisebit.scpescape.fsm.states.*
import com.onewisebit.scpescape.game.activity.GameContract
import com.onewisebit.scpescape.game.composable.*
import com.onewisebit.scpescape.model.entities.Vote
import com.onewisebit.scpescape.model.parsed.*
import com.onewisebit.scpescape.utilities.*

open class GamePresenterImpl(
    var gameView: GameContract.GameView,
    val gameModel: GameContract.GameModel,
    val gamePresenter: ContractGame.PresenterGame,
    val roundPresenter: ContractRound.PresenterRound,
    val turnPresenter: ContractTurn.PresenterTurn,
    val participantPresenter: ContractParticipant.PresenterParticipant,
    val playerPresenter: ContractPlayer.PresenterPlayer,
    val actionPresenter: ContractAction.PresenterAction,
    val votePresenter: ContractVote.PresenterVote,
    val modePresenter: ContractMode.PresenterMode,
    val gameID: Long
) : GameContract.GamePresenter,
    ContractGame.PresenterGame by gamePresenter,
    ContractRound.PresenterRound by roundPresenter,
    ContractTurn.PresenterTurn by turnPresenter,
    ContractParticipant.PresenterParticipant by participantPresenter,
    ContractPlayer.PresenterPlayer by playerPresenter,
    ContractAction.PresenterAction by actionPresenter,
    ContractVote.PresenterVote by votePresenter,
    ContractMode.PresenterMode by modePresenter {

    override fun onDestroy() {
    }

    override suspend fun setupPlayerTurnFragment() {

        val playerId = turnPresenter.getLatestTurn().playerID
        val playerName = playerPresenter.getPlayer(playerId).name
        val roleName: String = participantPresenter.getParticipant(playerId).roleName!!
        val roundName = roundPresenter.getCurrentRound()!!.details
        val actionDescription = actionPresenter.getPartialAction(roleName, roundName).description

        gameView.showPlayerTurnFragment(playerName, roleName, actionDescription)
    }

    override suspend fun setupPlayerPowerFragment() {

        val playerId = turnPresenter.getLatestTurn().playerID
        // role shouldn't be null at this point
        val roleName: String = participantPresenter.getParticipant(playerId).roleName!!
        // round shouldn't be null at this point
        val roundName = roundPresenter.getCurrentRound()!!.details
        val action = actionPresenter.getAction(roleName, roundName)
        val lastTurn: Boolean = turnPresenter.isLastTurn()

        when (action.extends) {
            POWER_VOTE -> {
                (action as VoteSettings).run {
                    gameView.showPlayerVoteFragment(roundName, roleName, lastTurn)
                }
            }
            POWER_INFO -> (action as InfoSettings).run {
                gameView.showPlayerInfoFragment(
                    this.information!!.title!!,
                    this.information!!.description!!,
                    lastTurn
                )
            }
            else -> throw IllegalArgumentException("No action found to extend: ${action.extends}")
        }

    }

    override suspend fun setupRoundResultsFragment() {
        //todo: parse here only the effects having VoteSettings.applied.end_round = true
        // and add one check after every turn for VoteSettings.applied.end_turn = true (maybe checking oldState = PlayerPower in  the activity)
        // make this more modular, something like applyEffect(action,votes), maybe move it to a EffectContract
        val roundVotes: MutableList<Vote> = mutableListOf()
        roundVotes.addAll(getLastRoundVotes())
        // load all the possible vote actions
        val voteActions = getModeActions().filterIsInstance<VoteSettings>()

        // players to be killed directly
        val deathCandidates: MutableList<Long> = mutableListOf()
        // players to be protected on direct hit
        val safePlayers: MutableList<Long> = mutableListOf()
        // players to be killed for sure, passed to view.showRoundResultFragment()
        val sureDeathPlayers: MutableSet<Long> = mutableSetOf()
        // players to be killed on another player's death. If the first one dies, so does the second.
        val pairedDeathPlayers: MutableList<Pair<Long, Long>> = mutableListOf()
        // used to control flow if round is to be replayed. Currently all effects will be skipped
        var replayRound = false

        voteLoop@ while (roundVotes.size > 0) {
            val currentVote = roundVotes.first()
            // I always process from the first vote, then remove all the used votes
            val action = voteActions.first { it.name == currentVote.voteAction }
            // all the votes involved in this action that will be removed from votes in the end
            val currentVotes: MutableSet<Vote> = mutableSetOf()
            // players that will be affected by the action effect
            val affectedPlayers: MutableList<Long> = mutableListOf()

            // check votes grouping
            val groupVote = action.voteGroup!!
            currentVotes.addAll(groupVotes(groupVote, roundVotes, currentVote))

            if (currentVotes.isNullOrEmpty())
                throw IllegalArgumentException("The current vote action is probably missing a group_vote option")


            // create a Map with <playerID,number of votes for that Player>
            val votesCount = currentVotes
                // group by voted player id
                .groupingBy { it.votedPlayerID }
                // count occurrences of this player id
                .eachCount()

            // get the maximum number of votes for a player
            val maxVotes: Int = votesCount.maxBy { it.value }!!.value

            // are there more than one player who got the max votes? (e.g. A got 2 votes, B got 5, C got 5, D got 4)
            if (votesCount.filter { it.value == maxVotes }.size > 1) {
                val draw = action.draw!!

                if (draw.reVoteAll == true || draw.reVoteNoDrawPlayers == true) {
                    roundPresenter.setCurrentRoundReplayable()
                    replayRound = true
                    break@voteLoop
                }

                affectedPlayers.addAll(choosePlayerOnDraw(draw, votesCount))

            } else {
                affectedPlayers.add(votesCount.filter { it.value == maxVotes }.keys.first())
            }

            // add players to corresponding effect lists

            //todo: move to function
            val effect = action.effect!!

            // add voted players to kill list
            if (effect.kill!!)
                deathCandidates.addAll(affectedPlayers)

            // add voted players to safe list
            if (effect.saveOnDeath!!)
                safePlayers.addAll(affectedPlayers)

            // add voting players to safe list
            if (effect.selfSavedIfTargeted!!)
                safePlayers.addAll(currentVotes.map { it.playerID })

            // add voting and voted players to paired list
            if (effect.dieOnDeath!!)
                currentVotes.forEach {
                    pairedDeathPlayers
                        .add(
                            Pair(
                                it.votedPlayerID,
                                it.playerID
                            )
                        )
                }


            // add voting players to kill list
            val participantList = getAliveParticipants()
            effect.dieIfRole?.let { deadlyRoleList ->
                if (deadlyRoleList.isNotEmpty()) {
                    // check players with roles in deadly roles list
                    val killers: List<Long> = participantList.filter { player ->
                        deadlyRoleList.contains(player.roleName)
                    }.map { it.playerID }
                    // add to death list players who voted players with these roles
                    sureDeathPlayers.addAll(
                        currentVotes.filter { vote ->
                            killers.contains(vote.votedPlayerID)
                        }.map { it.playerID }
                    )
                }
            }

            // remove used votes
            roundVotes.removeAll(currentVotes)
        }
        val message = mutableListOf<String>()
        if (!replayRound) {
            // populate kill list with the complete data from all the votes

            deathCandidates.forEach { candidate ->
                if (!safePlayers.contains(candidate))
                    sureDeathPlayers.add(candidate)
            }

            pairedDeathPlayers.forEach { playersPair ->
                if (sureDeathPlayers.contains(playersPair.first))
                    sureDeathPlayers.add(playersPair.second)
            }

            // kill players
            killParticipants(sureDeathPlayers.toList())

            // pass kill list to be shown
            sureDeathPlayers.forEach { id ->
                message.add(playerPresenter.getPlayer(id).name)
            }
        } else {
            //todo: read message from database/JSON
            message.add("Round to be replayed, tied votes")
        }
        gameView.showRoundResultFragment(message, replayRound)
    }

    private fun groupVotes(
        grouping: VoteGroup,
        roundVotes: List<Vote>,
        currentVote: Vote
    ): List<Vote> {
        val votes: MutableList<Vote> = mutableListOf()
        if (!grouping.actions.isNullOrEmpty())
            votes.addAll(roundVotes.filter { vote ->
                //todo: use containsIgnoreCase from my extension (test it first)
                grouping.actions!!.contains(vote.voteAction)
            })
        if (grouping.self == true)
            votes.add(currentVote)
        return votes
    }

    private fun choosePlayerOnDraw(drawSettings: Draw, votesCount: Map<Long, Int>): List<Long> {

        if (votesCount.size < 2)
            throw IllegalArgumentException("At least 2 players must have been voted for a tie to happen.")

        val players: MutableList<Long> = mutableListOf()

        if (drawSettings.random!!)
            players.add(votesCount.keys.random())

        if (drawSettings.maxRandom!!) {
            val maxVotes: Int = votesCount.maxBy { it.value }!!.value
            players.add(votesCount.filter { it.value == maxVotes }.keys.random())
        }

        if (drawSettings.ignore!!) {
            TODO("not implemented. Maybe this can be removed. Not adding affected players is like not having this option")
        }

        if (drawSettings.notApplicable!!)
            throw IllegalArgumentException("There was a tie in the votes, this shouldn't have happened (draw settings notApplicable=true)")

        return players
    }

    override suspend fun checkVictory() {
        // todo: check this code
        val victoryConditions: List<VictoryCondition> = modePresenter.getVictoryConditions()
        if (victoryConditions.isEmpty())
            throw java.lang.IllegalArgumentException("No victory conditions found for game $gameID")

        // victory reached?
        victoryConditions.forEach { condition ->

            when (condition.type) {
                DEAD_GROUP -> if (areGroupsDead(condition)) {
                    endGame(conditionReached = condition)
                    return
                }
                WLE_GROUP, WL_GROUP, WGE_GROUP, WG_GROUP ->
                    if (winningGroupsNumbersReached(condition)) {
                        endGame(conditionReached = condition)
                        return
                    }
                else -> throw java.lang.IllegalArgumentException("Condition ${condition.type} not implemented.")
            }
        }
        //victory not reached. Go to the next round.
        setupNextRound()
    }

    override suspend fun loadGame() {
        val save = gamePresenter.getSave()
        val oldStateName = save.stateMachineOld
        val newStateName = save.stateMachineNew
        if (oldStateName != null && newStateName != null) {
            val oldState = getStateFromName(oldStateName)
            val newState = getStateFromName(newStateName)
            gameView.loadGameState(oldState, newState)
        } else
            throw IllegalStateException("One of the game state was null for game $gameID: old state $oldStateName, new state $newStateName")
    }

    private fun getStateFromName(name: String): StateGame {

        return when (name) {
            CheckVictoryState().getName() -> CheckVictoryState()
            CloseGameState().getName() -> CloseGameState()
            EndGameState().getName() -> EndGameState()
            IntroState().getName() -> IntroState()
            PassDeviceState().getName() -> PassDeviceState()
            PlayerPowerState().getName() -> PlayerPowerState()
            PlayerTurnState().getName() -> PlayerTurnState()
            RoundInfoState().getName() -> RoundInfoState()
            ShowResultsState().getName() -> ShowResultsState()
            else -> throw IllegalArgumentException("No state found with name $name")
        }
    }

    private suspend fun setupNextRound() {
        gameView.nextRound()
    }

    private suspend fun areGroupsDead(condition: VictoryCondition): Boolean {
        val participants = participantPresenter.getAliveParticipants()
        participants.forEach { participant ->
            val group = participantPresenter.getGroup(participant.playerID)
            if (condition.first_groups.contains(group))
                return false
        }
        return true
    }

    private suspend fun winningGroupsNumbersReached(condition: VictoryCondition): Boolean {
        val participants = participantPresenter.getAliveParticipants()
        var firstGroup = 0
        var secondGroup = 0

        participants.forEach { participant ->
            val group = participantPresenter.getGroup(participant.playerID)
            if (condition.first_groups.contains(group))
                firstGroup += 1
            if (condition.second_groups.contains(group))
                secondGroup += 1
        }

        when (condition.type) {
            WLE_GROUP -> {
                return firstGroup <= secondGroup
            }
            WL_GROUP -> {
                return firstGroup < secondGroup
            }
            WGE_GROUP -> {
                return firstGroup >= secondGroup
            }
            WG_GROUP -> {
                return firstGroup > secondGroup
            }
            else -> return false
        }
    }

    private suspend fun endGame(conditionReached: VictoryCondition) {
        gameModel.setGameEnded(gameID)
        gameView.endGame(conditionReached.winner, conditionReached.message)
    }

    private suspend fun killParticipants(idList: List<Long>) {
        killParticipantsList(idList)
    }

    override suspend fun newPlayerTurn(): String {
        // if this is null either the game is finished and no more turn should have been created
        // or there was a problem retrieving participants
        val missingParticipants: List<Long> = gameModel.getMissingTurnsParticipants(gameID)!!
        // this is supposing the round is created before the turns
        val playerID = missingParticipants.random()
        gameModel.addTurn(gameID, playerID)
        //load and return player name
        return gameModel.getPlayerName(playerID)
            ?: throw IllegalArgumentException("No player's name found for player id $playerID")
    }

    companion object {
        private val TAG = GamePresenterImpl::class.java.simpleName
    }
}