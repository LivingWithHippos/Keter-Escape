package com.onewisebit.scpescape.fsm

data class Parameter(
    val name: String,
    val values: List<String>
) {

    fun getSingleBoolean(): Boolean {
        if (values.size == 1)
            return values[0].toBoolean()
        else
            throw IllegalArgumentException("Must have a single boolean value")
    }

    fun getSingleInt(): Int {
        if (values.size == 1)
            return values[0].toInt()
        else
            throw IllegalArgumentException("Must have a single Int value")
    }

    fun getSingleString(): String {
        if (values.size == 1)
            return values[0]
        else
            throw IllegalArgumentException("Must have a single String value")
    }

    fun getIntList(): List<Int> {
        return values.map { it.toInt() }
    }

    fun getBooleanList(): List<Boolean> {
        return values.map { it.toBoolean() }
    }

    fun getStringList(): List<String> {
        return values
    }
}