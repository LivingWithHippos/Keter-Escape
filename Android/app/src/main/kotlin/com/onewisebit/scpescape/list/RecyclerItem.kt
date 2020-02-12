package com.onewisebit.scpescape.list

interface RecyclerItem {
    val id: String?
    val type: Int
    override fun equals(other: Any?): Boolean
}