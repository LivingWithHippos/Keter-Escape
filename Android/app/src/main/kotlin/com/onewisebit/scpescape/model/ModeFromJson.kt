package com.onewisebit.scpescape.model

import com.google.gson.annotations.SerializedName

data class ModeFromJson (

    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("description") val description : String,
    @SerializedName("rules") val rules : String,
    @SerializedName("min") val min : Int,
    @SerializedName("max") val max : Int,
    @SerializedName("roles_division") val roles_division : List<RolesDivision>
    )

data class RolesDivision (

    @SerializedName("min_players") val min_players : Int,
    @SerializedName("max_players") val max_players : Int,
    @SerializedName("roles") val roles : List<Roles>
)

data class Roles (

    @SerializedName("role") val role : String,
    @SerializedName("quantity") val quantity : Int,
    @SerializedName("default") val default : Boolean
)