package com.onewisebit.scpescape.model.parsed

import com.google.gson.annotations.SerializedName

data class RoundDetails(
    @SerializedName("mode_id")
    val mode: Int,
    @SerializedName("code")
    val code: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String
)