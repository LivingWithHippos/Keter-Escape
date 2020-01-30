package com.onewisebit.scpescape.model.parsed
import com.google.gson.annotations.SerializedName


data class RoundDetails(
    @SerializedName("mode_id")
    val modeId: Int,
    @SerializedName("details")
    val details: List<Details>
)

data class Details(
    @SerializedName("code")
    val code: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String
)