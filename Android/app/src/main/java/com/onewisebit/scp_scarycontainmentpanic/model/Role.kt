package com.onewisebit.scp_scarycontainmentpanic.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "roles")
class Role(
    @PrimaryKey
    @ColumnInfo(name = "rolename")
    val name: String,
    @ColumnInfo(name = "roledescription")
    val description: String,
    @ColumnInfo(name = "rolegroup")
    val type: String
)