package com.onewisebit.scp_scarycontainmentpanic.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "roles")
class Role(
    @PrimaryKey
    @ColumnInfo(name = "role_name")
    val name: String,
    @ColumnInfo(name = "role_description")
    val description: String,
    @ColumnInfo(name = "role_group")
    val group: String
)