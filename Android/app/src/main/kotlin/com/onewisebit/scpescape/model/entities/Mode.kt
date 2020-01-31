package com.onewisebit.scpescape.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//TODO: if ModeDataClass works better remove this, repository and dao
@Entity(tableName = "modes")
data class Mode(
    @PrimaryKey
    @ColumnInfo(name = "mode_ID")
    val id: Int,
    @ColumnInfo(name = "mode_name")
    val name: String,
    @ColumnInfo(name = "mode_description")
    val description: String,
    @ColumnInfo(name = "mode_rules")
    val rules: String,
    @ColumnInfo(name = "max_players")
    val max: Int,
    @ColumnInfo(name = "min_players")
    val min: Int
)