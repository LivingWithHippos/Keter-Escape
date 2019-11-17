package com.onewisebit.scp_scarycontainmentpanic.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "modes")
data class Mode(
    @PrimaryKey
    @ColumnInfo(name = "modeID")
    val id: Int,
    @ColumnInfo(name = "modeName")
    val name: String,
    @ColumnInfo(name = "modeDescription")
    val description: String,
    @ColumnInfo(name = "modeRules")
    val rules: String
)