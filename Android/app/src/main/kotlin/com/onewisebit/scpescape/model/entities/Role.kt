package com.onewisebit.scpescape.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "roles",
    primaryKeys = ["mode", "role_name"],
    foreignKeys = [ForeignKey(
        entity = Mode::class,
        parentColumns = ["mode_ID"],
        childColumns = ["mode"],
        onDelete = ForeignKey.CASCADE
    )
    ],
    indices = [Index(value = ["role_name"], unique = true)]
)
class Role(
    @ColumnInfo(name = "mode")
    val modeId: Int,
    @ColumnInfo(name = "role_name")
    val name: String,
    @ColumnInfo(name = "role_description")
    val description: String,
    @ColumnInfo(name = "role_group")
    val group: String
)