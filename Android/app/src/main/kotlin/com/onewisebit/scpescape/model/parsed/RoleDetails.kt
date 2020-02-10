package com.onewisebit.scpescape.model.parsed

import com.google.gson.annotations.SerializedName


data class RoleDetails(
    @SerializedName("name")
    val name: String,
    @SerializedName("group")
    val group: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("round_actions")
    val roundActions: List<RoundAction>
)

data class RoundAction(
    @SerializedName("round_code")
    val roundCode: String,
    @SerializedName("action_name")
    val actionName: String
)