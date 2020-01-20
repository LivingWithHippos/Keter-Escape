package com.onewisebit.scpescape.model

data class ModeDataClass(
    val id: Int,
    val name: String,
    val shortDescription: String,
    val description: String,
    val rules: String,
    val min: String,
    val max: String,
    val rolesDivision: List<RolesDivision>
)

data class RolesDivision(
    val minPlayers: Int,
    val maxPlayers: Int,
    val roles: List<Role>
)

data class Role(
    val role: String,
    val quantity: Int,
    val default: Boolean
)