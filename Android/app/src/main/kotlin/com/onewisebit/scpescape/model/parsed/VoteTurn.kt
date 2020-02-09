package com.onewisebit.scpescape.model.parsed
import com.google.gson.annotations.SerializedName

data class VoteTurn(
    @SerializedName("extends")
    override val extends: String,
    @SerializedName("name")
    override val name: String,
    @SerializedName("description")
    override val description: String,
    @SerializedName("show")
    val show: Show,
    @SerializedName("reveal_role")
    val revealRole: RevealRole,
    @SerializedName("reveal_vote")
    val revealVote: RevealVote,
    @SerializedName("choice_enabled")
    val choiceEnabled: ChoiceEnabled,
    @SerializedName("choice_number")
    val choiceNumber: ChoiceNumber,
    @SerializedName("vote_group")
    val voteGroup: VoteGroup,
    @SerializedName("draw")
    val draw: Draw,
    @SerializedName("effect")
    val effect: Effect,
    @SerializedName("applied")
    val applied: Applied
) : TurnAction()

data class Show(
    @SerializedName("all")
    override val all: Boolean,
    @SerializedName("self")
    override val self: Boolean,
    @SerializedName("role")
    override val role: List<String>,
    @SerializedName("no_role")
    override val noRole: List<String>
): PlayerFilterOption()

data class RevealRole(
    @SerializedName("all")
    val all: Boolean,
    @SerializedName("self")
    val self: Boolean,
    @SerializedName("role")
    val role: List<String>,
    @SerializedName("no_role")
    val noRole: List<String>
)

data class RevealVote(
    @SerializedName("all")
    val all: Boolean,
    @SerializedName("self")
    val self: Boolean,
    @SerializedName("role")
    val role: List<String>,
    @SerializedName("no_role")
    val noRole: List<String>
)

data class ChoiceEnabled(
    @SerializedName("all")
    val all: Boolean,
    @SerializedName("self")
    val self: Boolean,
    @SerializedName("role")
    val role: List<String>,
    @SerializedName("no_role")
    val noRole: List<String>
)

data class ChoiceNumber(
    @SerializedName("exactly")
    val exactly: Int,
    @SerializedName("min")
    val min: String,
    @SerializedName("max")
    val max: String,
    @SerializedName("zero_allowed")
    val zeroAllowed: Boolean
)

data class VoteGroup(
    @SerializedName("self")
    val self: Boolean,
    @SerializedName("all")
    val all: Boolean,
    @SerializedName("group")
    val group: List<String>,
    @SerializedName("action")
    val action: Boolean
)

data class Draw(
    @SerializedName("re_vote_all")
    val reVoteAll: Boolean,
    @SerializedName("re_vote_no_draw_players")
    val reVoteNoDrawPlayers: Boolean,
    @SerializedName("random")
    val random: Boolean,
    @SerializedName("max_random")
    val maxRandom: Boolean,
    @SerializedName("ignore")
    val ignore: Boolean,
    @SerializedName("not_applicable")
    val notApplicable: Boolean
)

data class Effect(
    @SerializedName("kill")
    val kill: Boolean,
    @SerializedName("save_on_death")
    val saveOnDeath: Boolean,
    @SerializedName("self_saved_if_targeted")
    val selfSavedIfTargeted: Boolean,
    @SerializedName("die_on_death")
    val dieOnDeath: Boolean,
    @SerializedName("die_if_group")
    val dieIfGroup: List<String>,
    @SerializedName("die_if_role")
    val dieIfRole: List<String>
)

data class Applied(
    @SerializedName("end_turn")
    val endTurn: Boolean,
    @SerializedName("end_round")
    val endRound: Boolean,
    @SerializedName("last_voter")
    val lastVoter: Boolean
)