package com.onewisebit.scp_scarycontainmentpanic.model

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RoleRepository(private val roleDAO: RoleDAO) : InRoleRepository {

    override fun getAllRoles(): List<Role> = roleDAO.getAllRoles()

    override fun getRoleByName(name: String) = roleDAO.getRoleByName(name)

    override fun insertRole(role: Role) {
        roleDAO.insertRole(role)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.d(TAG, "Insert Success") },
                { Log.d(TAG, "Insert Error") }
            )
    }

    override suspend fun insertAll(roles: List<Role>) = roleDAO.insertAll(roles)

    companion object {
        private val TAG = RoleRepository::class.java.simpleName
    }
}