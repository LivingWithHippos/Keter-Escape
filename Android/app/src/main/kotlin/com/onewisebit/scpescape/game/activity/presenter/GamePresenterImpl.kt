package com.onewisebit.scpescape.game.activity.presenter

import com.onewisebit.scpescape.game.activity.GameContract
import com.onewisebit.scpescape.game.composable.*
import com.onewisebit.scpescape.model.entities.Vote
import com.onewisebit.scpescape.model.parsed.InfoSettings
import com.onewisebit.scpescape.model.parsed.VoteSettings
import com.onewisebit.scpescape.utilities.POWER_INFO
import com.onewisebit.scpescape.utilities.POWER_VOTE

open class GamePresenterImpl(
    var gameView: GameContract.GameView,
    val gameModel: GameContract.GameModel,
    val roundPresenter: ContractRound.PresenterRound,
    val turnPresenter: ContractTurn.PresenterTurn,
    val participantPresenter: ContractParticipant.PresenterParticipant,
    val playerPresenter: ContractPlayer.PresenterPlayer,
    val actionPresenter: ContractAction.PresenterAction,
    val votePresenter: ContractVote.PresenterVote,
    val gameID: Long
) : GameContract.GamePresenter,
    ContractRound.PresenterRound by roundPresenter,
    ContractTurn.PresenterTurn by turnPresenter,
    ContractParticipant.PresenterParticipant by participantPresenter,
    ContractPlayer.PresenterPlayer by playerPresenter,
    ContractAction.PresenterAction by actionPresenter,
    ContractVote.PresenterVote by votePresenter{

    override fun onDestroy() {
    }

    override suspend fun setupPlayerTurnFragment() {

        val playerId = turnPresenter.getLatestTurn().playerID
        val playerName = playerPresenter.getPlayer(playerId).name
        val roleName: String = participantPresenter.getParticipant(playerId).roleName!!
        val roundName = roundPresenter.getCurrentRound().details
        val actionDescription = actionPresenter.getPartialAction(roleName, roundName).description

        gameView.showPlayerTurnFragment(playerName, roleName, actionDescription)
    }

    override suspend fun setupPlayerPowerFragment() {

        val playerId = turnPresenter.getLatestTurn().playerID
        val roleName: String = participantPresenter.getParticipant(playerId).roleName!!
        val roundName = roundPresenter.getCurrentRound().details
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
        val votes:MutableList<Vote> = mutableListOf()
        votes.addAll(getLastRoundVotes())
        val voteActions = getModeActions().filterIsInstance<VoteSettings>()

        // players to be killed directly
        val deathCandidates: MutableList<Long> = mutableListOf()
        // players to be protected on direct hit
        val safePlayers: MutableList<Long> = mutableListOf()
        // players to be killed for sure, passed to view.showRoundResultFragment()
        val sureDeathPlayers: MutableSet<Long> = mutableSetOf()
        // players to be killed on another player's death. If the first one dies, so does the second.
        val pairedDeathPlayers: MutableList<Pair<Long,Long>> = mutableListOf()

        voteLoop@ while (votes.size > 0) {
            // I always process from the first vote, then remove all the used votes
            val action = voteActions.first{ it.name == votes.first().voteAction }
            // all the votes involved in this action that will be removed from votes in the end
            val currentVotes: MutableList<Vote> = mutableListOf()
            // players that will be affected by the action effect
            val affectedPlayers: MutableList<Long> = mutableListOf()
            /*
            controlla raggruppamento "vote_group"
            controlla parità "draw"
            controlla effetto "effect"
            controlla quando applicare "applied"
             */
            // check vote grouping
            //todo: move to function
            val groupVote = action.voteGroup!!
            if (groupVote.action!!){
                currentVotes.addAll(votes.filter { it.voteAction == action.name })
            }else{
                if (groupVote.self!!){
                    currentVotes.add(votes.first())
                }
                else {
                    throw IllegalArgumentException("The action ${action.name} needs a setting for vote_group")
                }
            }
            // check if it's a draw and what to do in that case
            val votedPlayersId: HashSet<Long> = hashSetOf()
            votedPlayersId.addAll(currentVotes.map { it.votedPlayerID })
            if (votedPlayersId.size > 1) {
                //todo: move to function
                val draw = action.draw!!

                if (draw.reVoteAll!! || draw.reVoteNoDrawPlayers!!){
                    TODO("not implemented. For daily vote we need to start another day round.")
                }

                if (draw.random!!)
                    affectedPlayers.add(votedPlayersId.random())

                if (draw.maxRandom!!){
                    val voteCount = currentVotes
                            // group by voted player id
                        .groupingBy { it.votedPlayerID }
                            // count occurrences of this player id
                        .eachCount()
                    // get the maximum number of votes for a player
                    val maxVotes = voteCount.maxBy { it.value }!!.value
                    affectedPlayers.add(
                        voteCount
                                // get only the ones with a max vote
                            .filter { it.value==maxVotes }
                                // get the keys (voted player id)
                            .keys
                                // get a random one
                            .random())
                }

                if(draw.ignore!!){
                    TODO("not implemented. Maybe this can be removed. Not adding affected players is like not having this option")
                }

                if(draw.notApplicable!!){
                    TODO("not implemented. Throw an exception maybe since we shouldn't have multiple voted with this option.")
                }

            }else{
                affectedPlayers.add(votedPlayersId.first())
            }

            // add to corresponding lists

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
                currentVotes.forEach{
                    pairedDeathPlayers
                    .add(
                        Pair(
                            it.votedPlayerID,
                            it.playerID)
                    )
                }


            // add voting players to kill list
            val participantList = getAliveParticipants()
            if (!effect.dieIfRole.isNullOrEmpty())
                currentVotes.forEach { vote ->
                    if ( effect.dieIfRole!!
                            .contains(
                                participantList
                                    .first{ vote.votedPlayerID == it.playerID }
                                    .roleName!!
                            ))

                        sureDeathPlayers.add(vote.playerID)
                }

            // remove used votes
            //todo: check if it works
            votes.removeAll(currentVotes)
        }

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
        gameView.showRoundResultFragment(sureDeathPlayers.toList())
    }

    private suspend fun killParticipants(idList: List<Long>){
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
}