package com.onewisebit.scp_scarycontainmentpanic.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "modes")
data class Mode(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "mode_ID")
    val id: Long,
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