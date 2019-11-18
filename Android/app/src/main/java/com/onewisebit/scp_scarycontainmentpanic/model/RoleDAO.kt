package com.onewisebit.scp_scarycontainmentpanic.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface RoleDAO {

    @Query("SELECT * FROM roles WHERE role_name = :name")
    fun getRoleByName(name: String): Flowable<Role>

    @Query("SELECT * FROM roles")
    fun getAllRoles(): List<Role>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRole(role: Role): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(roles: List<Role>)
}