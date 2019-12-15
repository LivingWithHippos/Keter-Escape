package com.onewisebit.scpescape.model.entities

import androidx.room.*

@Entity(tableName = "rounds",
    primaryKeys = ["game", "round_number"],
    foreignKeys = [ForeignKey(
        entity = Game::class,
        parentColumns = ["game_ID"],
        childColumns = ["game"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["game","round_number"],unique = true)]
)
//TODO: evaluate if this can be flattened into turn as a field or if it should be kept separated for expansion purposes
data class Round (
    // game is not set as primary key because we can't use autoGenerate
    // on a single field of a composite key. We use an index with
    // unique = true to preserve the uniqueness property of the primary key
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "number")
    val num: Int,
    @ColumnInfo(name = "game")
    val gameID: Long
)