package com.onewisebit.scpescape.model.parsed

import com.google.gson.annotations.SerializedName


data class InfoSettings(
    @SerializedName("extends")
    override var extends: String,
    @SerializedName("name")
    override var name: String,
    @SerializedName("description")
    override var description: String,
    @SerializedName("information")
    var information: Information?,
    @SerializedName("settings")
    var settings: Settings?
) : TurnAction {
    // see [VoteTurn.kt] for info about this
    override fun merge(derived: Mergeable) {
        if (derived is InfoSettings) {
            extends = derived.extends
            name = derived.name
            description = derived.description

            derived.information?.let { information!!.merge(it) }
            derived.settings?.let { settings!!.merge(it) }
        } else
            throw IllegalArgumentException("Merging class was not a VoteTurn one but $derived")
    }
}

data class Information(
    @SerializedName("title")
    var title: String?,
    @SerializedName("description")
    var description: String?
) : Mergeable {
    override fun merge(derived: Mergeable) {
        if (derived is Information) {
            derived.title?.let { title = it }
            derived.description?.let { description = it }
        }
    }
}

data class Settings(
    @SerializedName("background")
    var background: Background?,
    @SerializedName("timer")
    var timer: Timer?
) : Mergeable {
    override fun merge(derived: Mergeable) {
        if (derived is Settings) {
            derived.background?.let { background = it }
            derived.timer?.let { timer!!.merge(it) }
        }
    }
}

data class Background(
    @SerializedName("active")
    var active: Boolean?,
    @SerializedName("color")
    var color: String?
) : Mergeable {
    override fun merge(derived: Mergeable) {
        if (derived is Background) {
            derived.active?.let { active = it }
            derived.color?.let { color = it }
        }
    }
}

data class Timer(
    @SerializedName("active")
    var active: Boolean?,
    @SerializedName("seconds")
    var seconds: Int?
) : Mergeable {
    override fun merge(derived: Mergeable) {
        if (derived is Timer) {
            derived.active?.let { active = it }
            derived.seconds?.let { seconds = it }
        }
    }
}
