package com.onewisebit.scpescape.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "games",
    indices = [Index(value = arrayOf("mode"))]
)
data class Game(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "game_ID")
    val id: Long,
    @ColumnInfo(name = "mode")
    val modeID: Int,
    @ColumnInfo(name = "game_type")
    val type: Int,
    //TODO: test this value
    @ColumnInfo(name = "temporary", defaultValue = "true")
    val temp: Boolean
)