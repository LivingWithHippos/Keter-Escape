package com.onewisebit.scp_scarycontainmentpanic.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "players")
data class Player(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "player_ID")
    val id: Long,
    @ColumnInfo(name = "player_name")
    val name: String,
    //TODO: create library of pics to choose from
    @ColumnInfo(name = "player_image")
    val image: String
)