package com.onewisebit.scpescape.model.entities

import androidx.room.*

@Entity(
    tableName = "states",
    //primaryKeys = ["game", "round","affected_player"],
    foreignKeys = [
        ForeignKey(
            entity = Participant::class,
            parentColumns = ["game", "player"],
            childColumns = ["game", "affected_player"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Round::class,
            parentColumns = ["game", "number"],
            childColumns = ["game", "round"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Turn::class,
            parentColumns = ["turn_number", "round", "game", "player"],
            childColumns = ["turn", "round", "game", "affected_player"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["game", "affected_player"]), Index(value = ["turn", "round", "game", "affected_player"]), Index(
        value = ["game", "round"]
    )]
)
data class State(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "game")
    val gameID: Long,
    @ColumnInfo(name = "round")
    val roundNumber: Int,
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