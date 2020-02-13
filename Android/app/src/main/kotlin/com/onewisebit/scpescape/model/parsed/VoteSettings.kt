package com.onewisebit.scpescape.model.parsed

import com.google.gson.annotations.SerializedName

//TODO: rename this to voteAction and rename Action in fsm to transition or something else
/**
 * This class is used to parse all the vote.json.
 * The first three parameters are implementing TurnAction and must be passed in very json.
 * Only the template needs to include every value. Other vote action can extend vote and each other and
 * be merged one another calling merge(). Use is template.merge(voteTurn), if more than one needs to be extended
 * they must be extended from the template to the needed VoteTurn:
 * if voteTurnA extends voteTurnB and voteTurnB extends voteTurnC we need to call merge with the order
 * template.merge(voteTurnC)
 * template.merge(voteTurnB)
 * template.merge(voteTurnA)
 * for now the edits are in place, check if it would be better to make merge() return the class instead
 */
data class VoteSettings(
    @SerializedName("extends")
    override var extends: String,
    @SerializedName("name")
    override var name: String,
    @SerializedName("description")
    override var description: String,
    @SerializedName("show")
    var show: PlayerFilter?,
    @SerializedName("reveal_role")
    var revealRole: PlayerFilter?,
    @SerializedName("reveal_vote")
    var revealVote: PlayerFilter?,
    @SerializedName("choice_enabled")
    var choiceEnabled: PlayerFilter?,
    @SerializedName("choice_number")
    var choiceNumber: ChoiceNumber?,
    @SerializedName("vote_group")
    var voteGroup: VoteGroup?,
    @SerializedName("draw")
    var draw: Draw?,
    @SerializedName("effect")
    var effect: Effect?,
    @SerializedName("applied")
    var applied: Applied?
) : TurnAction {
    // if the merging values are different from null, load them
    // merge needs to be used from the template -> templateVote.merge(loadedVote)
    override fun merge(derived: Mergeable) {
        if (derived is VoteSettings){
            // primitives can be assigned directly, other data classes must use merge()
            extends = derived.extends
            name = derived.name
            description = derived.description
            // using !! and not ? since we MUST call this from the template, and get an error otherwise
            // by using let we don't need to use show, reveal_role etc in the extending vote powers
            // if their value correspond to the template ones
            derived.show?.let { show!!.merge(it) }
            derived.revealRole?.let { revealRole!!.merge(it) }
            derived.revealVote?.let { revealVote!!.merge(it) }
            derived.choiceEnabled?.let { choiceEnabled!!.merge(it) }
            derived.choiceNumber?.let { choiceNumber!!.merge(it) }
            derived.voteGroup?.let { voteGroup!!.merge(it) }
            derived.draw?.let { draw!!.merge(it) }
            derived.effect?.let { effect!!.merge(it) }
            derived.applied?.let { applied!!.merge(it) }
        }
        else
            throw IllegalArgumentException("Merging class was not a VoteTurn one but $derived")
    }
}

data class PlayerFilter(
    @SerializedName("all")
    var all: Boolean?,
    @SerializedName("role")
    var role: List<String>?,
    @SerializedName("no_role")
    var noRole: List<String>?,
    @SerializedName("self")
    var self: Boolean?
): Mergeable {
    override fun merge(derived: Mergeable) {
        if (derived is PlayerFilter){
            derived.all?.let { all = it }
            derived.role?.let { role = it }
            derived.noRole?.let { noRole = it }
            derived.self?.let { self = it }
        }
    }
}

data class ChoiceNumber(
    @SerializedName("exactly")
    var exactly: Int?,
    @SerializedName("min")
    var min: String?,
    @SerializedName("max")
    var max: String?,
    @SerializedName("zero_allowed")
    var zeroAllowed: Boolean?
): Mergeable {
    override fun merge(derived: Mergeable) {
        if (derived is ChoiceNumber){
            derived.exactly?.let { exactly = it }
            derived.min?.let { min = it }
            derived.max?.let { max = it }
            derived.zeroAllowed?.let { zeroAllowed = it }
        }
    }
}

data class VoteGroup(
    @SerializedName("all")
    var all: Boolean?,
    @SerializedName("role")
    var role: List<String>?,
    @SerializedName("action")
    var action: Boolean?,
    @SerializedName("self")
    var self: Boolean?
): Mergeable {
    override fun merge(derived: Mergeable) {
        if (derived is VoteGroup){
            derived.all?.let { all = it }
            derived.role?.let { role = it }
            derived.action?.let { action = it }
            derived.self?.let { self = it }
        }
    }
}

data class Draw(
    @SerializedName("re_vote_all")
    var reVoteAll: Boolean?,
    @SerializedName("re_vote_no_draw_players")
    var reVoteNoDrawPlayers: Boolean?,
    @SerializedName("random")
    var random: Boolean?,
    @SerializedName("max_random")
    var maxRandom: Boolean?,
    @SerializedName("ignore")
    var ignore: Boolean?,
    @SerializedName("not_applicable")
    var notApplicable: Boolean?
):Mergeable{
    override fun merge(derived: Mergeable) {
        if (derived is Draw) {
            derived.reVoteAll?.let { reVoteAll = it }
            derived.reVoteNoDrawPlayers?.let { reVoteNoDrawPlayers = it }
            derived.random?.let { random = it }
            derived.maxRandom?.let { maxRandom = it }
            derived.ignore?.let { ignore = it }
            derived.notApplicable?.let { notApplicable = it }
        }
    }
}

data class Effect(
    @SerializedName("kill")
    var kill: Boolean?,
    @SerializedName("save_on_death")
    var saveOnDeath: Boolean?,
    @SerializedName("self_saved_if_targeted")
    var selfSavedIfTargeted: Boolean?,
    @SerializedName("die_on_death")
    var dieOnDeath: Boolean?,
    @SerializedName("die_if_group")
    var dieIfGroup: List<String>?,
    @SerializedName("die_if_role")
    var dieIfRole: List<String>?
):Mergeable{
    override fun merge(derived: Mergeable) {
        if (derived is Effect) {
            derived.kill?.let { kill = it }
            derived.saveOnDeath?.let { saveOnDeath = it }
            derived.selfSavedIfTargeted?.let { selfSavedIfTargeted = it }
            derived.dieOnDeath?.let { dieOnDeath = it }
            derived.dieIfGroup?.let { dieIfGroup = it }
            derived.dieIfRole?.let { dieIfRole = it }
        }
    }
}

data class Applied(
    @SerializedName("end_turn")
    var endTurn: Boolean?,
    @SerializedName("end_round")
    var endRound: Boolean?,
    @SerializedName("last_voter")
    var lastVoter: Boolean?
):Mergeable{
    override fun merge(derived: Mergeable) {
        if (derived is Applied) {
            derived.endTurn?.let { endTurn = it }
            derived.endRound?.let { endRound = it }
            derived.lastVoter?.let { lastVoter = it }
        }
    }
}