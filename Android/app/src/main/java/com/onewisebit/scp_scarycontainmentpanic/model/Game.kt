package com.onewisebit.scp_scarycontainmentpanic.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class Game(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "game_ID")
    val id: Long,
    @ColumnInfo(name = "game_type")
    val type: Int,
    //TODO: test this value
    @ColumnInfo(name = "temporary", defaultValue = "true")
    val temp: Boolean
)