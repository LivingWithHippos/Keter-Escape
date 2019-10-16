package com.onewisebit.scp_scarycontainmentpanic.model

import io.reactivex.Flowable

interface InRoleRepository {

    fun getRoleByName(name: String): Flowable<Role>

    fun getAllRoles(): List<Role>

    fun insertRole(role: Role)

    suspend fun insertAll(roles: List<Role>)
}