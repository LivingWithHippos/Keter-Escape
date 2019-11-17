package com.onewisebit.scp_scarycontainmentpanic.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "players")
data class Player(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "playerID")
    val id: Long,
    @ColumnInfo(name = "playername")
    val name: String,
    //TODO: create library of pics to choose from
    @ColumnInfo(name = "playerimage")
    val image: String
)