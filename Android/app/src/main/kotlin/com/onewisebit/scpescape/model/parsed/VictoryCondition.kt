package com.onewisebit.scpescape.model.parsed


data class VictoryCondition(
    val type: String,
    val first_groups: List<String>,
    val second_groups: List<String>,
    val message: String
)