package com.onewisebit.scpescape.model.entities

import androidx.room.*

@Entity(
    tableName = "games",
    foreignKeys = [
        ForeignKey(
            entity = Mode::class,
            parentColumns = ["mode_ID"],
            childColumns = ["mode"],
            onDelete = ForeignKey.NO_ACTION )]
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