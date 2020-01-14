package com.onewisebit.scpescape.model

import com.google.gson.annotations.SerializedName

data class ActionVote (

        @SerializedName("extends") val extends : String,
        @SerializedName("name") val name : String,
        @SerializedName("description") val description : String,
        @SerializedName("show") val show : Show,
        @SerializedName("reveal_role") val reveal_role : RevealRole,
        @SerializedName("choice_enabled") val choice_enabled : ChoiceEnabled,
        @SerializedName("choice_number") val choice_number : ChoiceNumber,
        @SerializedName("vote_group") val vote_group : VoteGroup,
        @SerializedName("on_draw") val on_draw : Draw,
        @SerializedName("effect") val effect : Effect,
        @SerializedName("applied") val applied : Applied

)

data class Show (

    @SerializedName("all") val all : Boolean = false,
    @SerializedName("self") val self : Boolean = false,
    @SerializedName("role") val role : List<String> = emptyList(),
    @SerializedName("no_role") val no_role : List<String> = emptyList()
)

data class RevealRole (

    @SerializedName("all") val all : Boolean = false,
    @SerializedName("none") val none : Boolean = false,
    @SerializedName("role") val role : List<String> = emptyList(),
    @SerializedName("no_role") val no_role : List<String> = emptyList()
)

data class ChoiceEnabled (

    @SerializedName("all") val all : Boolean = false,
    @SerializedName("role") val role : List<String> = emptyList(),
    @SerializedName("no_role") val no_role : List<String> = emptyList()
)

data class ChoiceNumber (

    @SerializedName("exactly") val exactly : Int,
    @SerializedName("min") val min : Int,
    @SerializedName("max") val max : Int,
    @SerializedName("zero_allowed") val zero_allowed : Boolean = false
)

data class Effect (

    @SerializedName("kill") val kill : Boolean = false,
    @SerializedName("save_on_death") val save_on_death : Boolean = false,
    @SerializedName("self_saved_if_targeted") val self_saved_if_targeted : Boolean = false,
    @SerializedName("die_on_death") val die_on_death : Boolean = false,
    @SerializedName("die_if_group") val die_if_group : List<String> = emptyList()
)

data class Draw (

    @SerializedName("re_vote_no_draw_players") val re_vote_no_draw_players : Boolean = false,
    @SerializedName("re_vote_all") val re_vote_all : Boolean = false,
    @SerializedName("random") val random : Boolean = false,
    @SerializedName("max_random") val max_random : Boolean = false,
    @SerializedName("ignore") val ignore : Boolean = false,
    @SerializedName("not_applicable") val not_applicable : Boolean = false
)

data class VoteGroup (

    @SerializedName("single") val single : Boolean = false,
    @SerializedName("all") val all : Boolean = false,
    @SerializedName("group") val group : List<String> = emptyList()
)

data class Applied (

    @SerializedName("end_turn") val end_turn : Boolean = false,
    @SerializedName("end_round") val end_round : Boolean = false,
    @SerializedName("last_voter") val last_voter : Boolean = false
)