package com.onewisebit.scpescape.model.entities

import androidx.room.*

@Entity(
    tableName = "states",
    //primaryKeys = ["game", "round","affected_player"],
    foreignKeys = [
        ForeignKey(
            entity = Round::class,
            parentColumns = ["game","number"],
            childColumns = ["game","round"],
            onDelete = ForeignKey.CASCADE
        ),
    ForeignKey(
        entity = Player::class,
        parentColumns = ["player_ID"],
        childColumns = ["affected_player"],
        onDelete = ForeignKey.NO_ACTION )
    ],
    indices = [Index(value = ["game", "round"]), Index(value = ["affected_player"])]
)
data class State (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "game")
    val gameID: Long,
    @ColumnInfo(name = "round")
    val num: Int,
    //this is not a Turn foreign key because there are two effect (maybe)
    //effect applied on turn and effect applied on the end of the round.
    // For those last ones turn is null
    @ColumnInfo(name = "turn")
    val turnNumber: Int?,
    @ColumnInfo(name = "affected_player")
    val affectedPlayerID: Long,
    @ColumnInfo(name = "active", defaultValue = "true")
    val active: Boolean
)