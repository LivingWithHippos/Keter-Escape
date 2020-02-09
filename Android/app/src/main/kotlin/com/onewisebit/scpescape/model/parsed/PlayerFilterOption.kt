package com.onewisebit.scpescape.model.parsed

abstract class PlayerFilterOption {
    abstract val all: Boolean
    abstract val self: Boolean
    abstract val role: List<String>
    abstract val noRole: List<String>
}