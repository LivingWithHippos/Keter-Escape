package com.onewisebit.scpescape.fsm
import com.google.gson.annotations.SerializedName


data class VoteJsonDataClass(
    @SerializedName("extends")
    override val extends: String,
    @SerializedName("name")
    override val name: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("show")
    val show: VoteShow?,
    @SerializedName("reveal_role")
    val revealRole: VoteRevealRole?,
    @SerializedName("choice_enabled")
    val choiceEnabled: VoteChoiceEnabled?,
    @SerializedName("choice_number")
    val choiceNumber: VoteChoiceNumber?,
    @SerializedName("vote_group")
    val voteGroup: VoteGroup?,
    @SerializedName("draw")
    val draw: VoteDraw?,
    @SerializedName("effect")
    val effect: VoteEffect?,
    @SerializedName("applied")
    val applied: VoteApplied?
) : Input

data class VoteShow(
    @SerializedName("all")
    val all: Boolean?,
    @SerializedName("self")
    val self: Boolean?,
    @SerializedName("role")
    val role: List<Any?>?,
    @SerializedName("no_role")
    val noRole: List<Any?>?
)

data class VoteRevealRole(
    @SerializedName("all")
    val all: Boolean?,
    @SerializedName("role")
    val role: List<Any?>?,
    @SerializedName("no_role")
    val noRole: List<Any?>?
)

data class VoteChoiceEnabled(
    @SerializedName("all")
    val all: Boolean?,
    @SerializedName("self")
    val self: Boolean?,
    @SerializedName("role")
    val role: List<Any?>?,
    @SerializedName("no_role")
    val noRole: List<Any?>?
)

data class VoteChoiceNumber(
    @SerializedName("exactly")
    val exactly: Int?,
    @SerializedName("min")
    val min: Any?,
    @SerializedName("max")
    val max: Any?,
    @SerializedName("zero_allowed")
    val zeroAllowed: Boolean?
)

data class VoteGroup(
    @SerializedName("single")
    val single: Boolean?,
    @SerializedName("all")
    val all: Boolean?,
    @SerializedName("group")
    val group: List<Any?>?,
    @SerializedName("action")
    val action: Boolean?
)

data class VoteDraw(
    @SerializedName("re_vote_no_draw_players")
    val reVoteNoDrawPlayers: Boolean?,
    @SerializedName("re_vote_all")
    val reVoteAll: Boolean?,
    @SerializedName("random")
    val random: Boolean?,
    @SerializedName("max_random")
    val maxRandom: Boolean?,
    @SerializedName("ignore")
    val ignore: Boolean?,
    @SerializedName("not_applicable")
    val notApplicable: Boolean?
)

data class VoteEffect(
    @SerializedName("kill")
    val kill: Boolean?,
    @SerializedName("save_on_death")
    val saveOnDeath: Boolean?,
    @SerializedName("self_saved_if_targeted")
    val selfSavedIfTargeted: Boolean?,
    @SerializedName("die_on_death")
    val dieOnDeath: Boolean?,
    @SerializedName("die_if_group")
    val dieIfGroup: List<Any?>?
)

data class VoteApplied(
    @SerializedName("end_turn")
    val endTurn: Boolean?,
    @SerializedName("end_round")
    val endRound: Boolean?,
    @SerializedName("last_voter")
    val lastVoter: Boolean?
)