package com.onewisebit.scp_scarycontainmentpanic.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(primaryKeys = ["gameID","playerID"],
    tableName = "participants",
    foreignKeys = arrayOf(
        ForeignKey(entity = Game::class,
            parentColumns = ["id"],
            childColumns = ["game"],
            //TODO: implement logic to manage the deletion of a player with a saved/running game
            onDelete = ForeignKey.NO_ACTION),
        ForeignKey(entity = Player::class,
            parentColumns = ["id"],
            childColumns = ["player"],
            onDelete = ForeignKey.NO_ACTION),
        ForeignKey(entity = Role::class,
            parentColumns = ["name"],
            childColumns = ["role"],
            onDelete = ForeignKey.NO_ACTION)
    ))
data class Participant (
    @ColumnInfo(name="game")
    val gameID: Long,
    @ColumnInfo(name="player")
    val playerID: Long,
    @ColumnInfo(name="role")
    val roleName: String,
    @ColumnInfo(name="state")
    val stateValue: Int
)