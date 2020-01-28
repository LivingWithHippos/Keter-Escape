package com.onewisebit.scpescape.fsm

interface Input {
    val extends: String
    val name: String
}

interface PlayersFilter {
    val all: Boolean?
    val self: Boolean?
    val role: List<String>?
    val noRole: List<String>?
}