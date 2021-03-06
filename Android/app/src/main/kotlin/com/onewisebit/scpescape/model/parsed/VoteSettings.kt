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
    override fun merge(derived: Mergeable, mergeSettings: MergeSettings?) {
        if (derived is VoteSettings) {
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
        } else
            throw IllegalArgumentException("Merging class was not a VoteTurn one but $derived")
    }

    override fun toString(): String {
        return "VoteSettings(extends='$extends', name='$name', description='$description', show=$show, revealRole=$revealRole, revealVote=$revealVote, choiceEnabled=$choiceEnabled, choiceNumber=$choiceNumber, voteGroup=$voteGroup, draw=$draw, effect=$effect, applied=$applied)"
    }
}

//todo: add onlyAlive boolean? Could add powers over dead people
data class PlayerFilter(
    @SerializedName("all")
    var all: Boolean?,
    @SerializedName("role")
    var role: List<String>?,
    @SerializedName("no_role")
    var noRole: List<String>?,
    @SerializedName("self")
    var self: Boolean?
) : Mergeable {
    override fun merge(derived: Mergeable, mergeSettings: MergeSettings?) {
        if (derived is PlayerFilter) {
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
    //todo: check if these are ordered or if we need to change List<> to something else
    // see https://github.com/google/gson/blob/master/UserGuide.md#array-examples
    @SerializedName("range")
    var range: List<Int>?,
    @SerializedName("zero_allowed")
    var zeroAllowed: Boolean?
) : Mergeable {
    override fun merge(derived: Mergeable, mergeSettings: MergeSettings?) {
        if (derived is ChoiceNumber) {
            derived.exactly?.let { exactly = it }
            derived.range?.let { range = it }
            derived.zeroAllowed?.let { zeroAllowed = it }
        }
    }
}

// How to group the votes. For example the daily vote must be counted between everyone
// while the scp attack vote may be shared only among them
//TODO: rename to GroupVote or something else
data class VoteGroup(
    @SerializedName("actions")
    var actions: List<String>?,
    @SerializedName("self")
    var self: Boolean?
) : Mergeable {
    override fun merge(derived: Mergeable, mergeSettings: MergeSettings?) {
        if (derived is VoteGroup) {
            derived.actions?.let { actions = it }
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
) : Mergeable {
    override fun merge(derived: Mergeable, mergeSettings: MergeSettings?) {
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
    @SerializedName("die_if_role")
    var dieIfRole: List<String>?
) : Mergeable {
    override fun merge(derived: Mergeable, mergeSettings: MergeSettings?) {
        if (derived is Effect) {
            derived.kill?.let { kill = it }
            derived.saveOnDeath?.let { saveOnDeath = it }
            derived.selfSavedIfTargeted?.let { selfSavedIfTargeted = it }
            derived.dieOnDeath?.let { dieOnDeath = it }
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
) : Mergeable {
    override fun merge(derived: Mergeable, mergeSettings: MergeSettings?) {
        if (derived is Applied) {
            derived.endTurn?.let { endTurn = it }
            derived.endRound?.let { endRound = it }
            derived.lastVoter?.let { lastVoter = it }
        }
    }
}

