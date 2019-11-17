package com.onewisebit.scp_scarycontainmentpanic.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "participants",
    primaryKeys = ["game","player"],
    foreignKeys = arrayOf(
        ForeignKey(entity = Game::class,
            parentColumns = ["gameID"],
            childColumns = ["game"],
            //TODO: implement logic to manage the deletion of a player with a saved/running game
            onDelete = ForeignKey.NO_ACTION),
        ForeignKey(entity = Player::class,
            parentColumns = ["playerID"],
            childColumns = ["player"],
            onDelete = ForeignKey.NO_ACTION),
        ForeignKey(entity = Role::class,
            parentColumns = ["rolename"],
            childColumns = ["role"],
            onDelete = ForeignKey.NO_ACTION)
    ))
data class Participant (
    @ColumnInfo(name="game")
    val gameID: Long,
    @ColumnInfo(name="player", index = true)
    val playerID: Long,
    @ColumnInfo(name="role", index = true)
    val roleName: String,
    @ColumnInfo(name="state")
    val stateValue: Int
)