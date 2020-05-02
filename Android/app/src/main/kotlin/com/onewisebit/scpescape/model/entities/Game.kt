package com.onewisebit.scpescape.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "games",
    indices = [Index(value = ["game_ID", "mode"], unique = true)]
)
data class Game(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "game_ID")
    val id: Long,
    //todo: add Foreign key on mode
    @ColumnInfo(name = "mode")
    val modeID: Int,
    @ColumnInfo(name = "game_type")
    val type: Int,
    @ColumnInfo(name = "temporary", defaultValue = "true")
    val temp: Boolean,
    @ColumnInfo(name = "ended", defaultValue = "false")
    val ended: Boolean
)