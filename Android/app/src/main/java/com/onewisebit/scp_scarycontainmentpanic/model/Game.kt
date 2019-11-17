package com.onewisebit.scp_scarycontainmentpanic.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class Game(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "gameID")
    val id: Long,
    @ColumnInfo(name = "gameType")
    val type: Int,
    @ColumnInfo(name = "gameMode")
    val mode: Int
)