package com.onewisebit.scp_scarycontainmentpanic.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface RoleDAO {

    @Query("SELECT * FROM roles WHERE rolename = :name")
    fun getRoleByName(name: String): Flowable<Role>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRole(role: Role): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(roles: List<Role>)
}