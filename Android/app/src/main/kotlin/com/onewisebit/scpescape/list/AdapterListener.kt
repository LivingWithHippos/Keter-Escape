package com.onewisebit.scpescape.list

// todo: replace with higher order function
interface AdapterListener {
    fun listen(click: AdapterClick?)
}
interface AdapterClick