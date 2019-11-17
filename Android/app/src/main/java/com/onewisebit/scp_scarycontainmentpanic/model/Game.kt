package com.onewisebit.scp_scarycontainmentpanic.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "games")
data class Game(
    @PrimaryKey
    @ColumnInfo(name = "gameID")
    val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "gameType")
    val type: Int,
    @ColumnInfo(name = "gameMode")
    val mode: Int
)