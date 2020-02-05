package com.onewisebit.scpescape.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "rounds",
    primaryKeys = ["game", "number"],
    foreignKeys = [ForeignKey(
        entity = Game::class,
        parentColumns = ["game_ID","mode"],
        childColumns = ["game","mode"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["game", "mode"], unique = true)]
)
//TODO: evaluate if this can be flattened into turn as a field or if it should be kept separated for expansion purposes
data class Round(
    // game is not set as primary key because we can't use autoGenerate
    // on a single field of a composite key. We use an index with
    // unique = true to preserve the uniqueness property of the primary key
    @ColumnInfo(name = "number")
    val num: Int,
    @ColumnInfo(name = "game")
    val gameID: Long,
    @ColumnInfo(name = "mode")
    val modeID: Int,
    @ColumnInfo(name = "details")
    val details: String
)