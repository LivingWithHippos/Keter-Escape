package com.onewisebit.scpescape.model.parsed
import com.google.gson.annotations.SerializedName


data class InfoTurn(
    @SerializedName("extends")
    val extends: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("information")
    val information: Information,
    @SerializedName("settings")
    val settings: Settings
)

data class Information(
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String
)

data class Settings(
    @SerializedName("background")
    val background: Background,
    @SerializedName("timer")
    val timer: Timer
)

data class Background(
    @SerializedName("active")
    val active: Boolean,
    @SerializedName("color")
    val color: String
)

data class Timer(
    @SerializedName("active")
    val active: Boolean,
    @SerializedName("seconds")
    val seconds: Int
)