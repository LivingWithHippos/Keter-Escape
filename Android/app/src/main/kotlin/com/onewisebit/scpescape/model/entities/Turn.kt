package com.onewisebit.scpescape.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "turns",
    primaryKeys = ["round","game", "player"],
    foreignKeys = [ForeignKey(
        entity = Round::class,
        parentColumns = ["number","game"],
        childColumns = ["round","game"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Player::class,
        parentColumns = ["player_ID"],
        childColumns = ["player"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Turn (
    @ColumnInfo(name = "round")
    val roundNumber: Int,
    //TODO: decide if game can be taken from participant or round or if the entities need to be changed
    @ColumnInfo(name = "game")
    val gameID: Long,
    @ColumnInfo(name = "player", index = true)
    val playerID: Long
)