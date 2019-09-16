package com.onewisebit.scp_scarycontainmentpanic.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "players")
data class Player (
    @PrimaryKey
    @ColumnInfo(name = "playerid")
    val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "playername")
    val name: String,
    @ColumnInfo(name = "playerimage")
    val image: String
)