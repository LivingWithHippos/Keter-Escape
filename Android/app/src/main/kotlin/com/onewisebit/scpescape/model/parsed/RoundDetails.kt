package com.onewisebit.scpescape.model.parsed
import com.google.gson.annotations.SerializedName


data class RoundDetails(
    @SerializedName("mode_id")
    val modeId: Int,
    @SerializedName("roundDetails")
    val roundDetails: List<RoundDetail>
)

data class RoundDetail(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String
)