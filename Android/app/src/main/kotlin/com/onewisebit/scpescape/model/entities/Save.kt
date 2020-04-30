package com.onewisebit.scpescape.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "saves",
    foreignKeys = [
        ForeignKey(
            entity = Game::class,
            parentColumns = ["game_ID"],
            childColumns = ["game"],
            onDelete = ForeignKey.CASCADE
        )]
)
data class Save(
    @PrimaryKey
    @ColumnInfo(name = "game")
    val game: Long,
    @ColumnInfo(name = "state_machine_old")
    val stateMachineOld: String?,
    @ColumnInfo(name = "state_machine_new")
    val stateMachineNew: String?,
    // useful if the last state is relative to a player that could be re-chosen
    @ColumnInfo(name = "player")
    val player: Long?,
    // useful if the state has some calculation already done that needs to be skipped
    @ColumnInfo(name = "state_processed", defaultValue = "false")
    val stateProcessed: Boolean
)