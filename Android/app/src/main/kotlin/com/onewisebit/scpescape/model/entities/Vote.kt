package com.onewisebit.scpescape.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "votes",
    foreignKeys = [ForeignKey(
        entity = Turn::class,
        parentColumns = ["turn_number","round", "game","player"],
        childColumns = ["turn","round", "game","player"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Player::class,
        parentColumns = ["player_ID"],
        childColumns = ["player_voted"],
        onDelete = ForeignKey.CASCADE
    )]
)
class Vote (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "vote_ID")
    val id: Long,
    @ColumnInfo(name = "round")
    val roundNumber: Int,
    @ColumnInfo(name = "turn")
    val turnNumber: Int,
    @ColumnInfo(name = "game")
    val gameID: Long,
    @ColumnInfo(name = "player", index = true)
    val playerID: Long,
    @ColumnInfo(name = "player_voted")
    val votedPlayerID: Long,
    @ColumnInfo(name = "vote_type")
    val voteType: String
)