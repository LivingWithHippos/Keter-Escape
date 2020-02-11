package com.onewisebit.scpescape.list

interface RecyclerItem {
    val id: String?
    override fun equals(other: Any?): Boolean
}