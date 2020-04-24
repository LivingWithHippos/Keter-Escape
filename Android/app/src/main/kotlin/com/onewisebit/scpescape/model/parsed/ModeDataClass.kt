package com.onewisebit.scpescape.model.parsed

import com.google.gson.annotations.SerializedName

data class ModeDataClass(
    @SerializedName("id")
    val id: Int,
    @SerializedName("version")
    val version: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("short_description")
    val shortDescription: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("rules")
    val rules: String,
    @SerializedName("min")
    val min: Int,
    @SerializedName("max")
    val max: Int,
    @SerializedName("roles_division")
    val rolesDivision: List<RolesDivision>
)

data class RolesDivision(
    @SerializedName("min_players")
    val minPlayers: Int,
    @SerializedName("max_players")
    val maxPlayers: Int,
    @SerializedName("roles_details")
    val roles: List<RolesDetail>
)

data class RolesDetail(
    @SerializedName("role")
    val role: String,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("default")
    val default: Boolean
)