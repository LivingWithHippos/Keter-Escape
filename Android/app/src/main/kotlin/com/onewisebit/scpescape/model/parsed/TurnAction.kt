package com.onewisebit.scpescape.model.parsed

interface TurnAction: Mergeable {
    var extends: String
    var name: String
    var description: String
}

interface Mergeable{
    fun merge(derived: Mergeable)
}