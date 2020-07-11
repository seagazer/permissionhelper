package com.seagazer.permission

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData

/**
 * Author: Seagazer
 * Date: 2020/4/23
 *
 * A helper class with liveData to request permission.
 */
class PermissionHelper(activity: FragmentActivity) {
    private val liveData: MutableLiveData<PermissionResult> = MutableLiveData()
    private val appContext: Context = activity.application
    private val fragmentManager: FragmentManager = activity.supportFragmentManager
    private var fragment: PermissionFragment? = null

    companion object {
        const val TAG = "PermissionFragment"
    }

    /**
     * Check has the permission
     */
    fun hasPermission(permission: String): Boolean {
        return hasPermissions(arrayOf(permission))
    }

    /**
     * Check has the permissions
     */
    fun hasPermissions(permissions: Array<String>): Boolean {
        permissions.forEach {
            if (ActivityCompat.checkSelfPermission(appContext, it) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    /**
     * Request single permission
     * @param permission The permission to request
     */
    fun requestPermission(permission: String): MutableLiveData<PermissionResult> {
        requestPermissions(arrayOf(permission))
        return liveData
    }

    /**
     * Request permissions
     * @param permissions The permissions to request
     */
    fun requestPermissions(permissions: Array<String>): MutableLiveData<PermissionResult> {
        if (fragment == null) {
            lateInit()
        }
        fragment!!.requestPermissions(liveData, permissions)
        return liveData
    }

    private fun lateInit() {
        val fragmentByTag = fragmentManager.findFragmentByTag(TAG)
        if (fragmentByTag == null) {
            fragment = PermissionFragment()
            fragmentManager.beginTransaction().add(fragment!!, TAG).commitNow()
        } else {
            fragment = fragmentByTag as PermissionFragment?
        }
    }
}

data class PermissionResult(val type: PermissionResultType, val permissions: MutableList<String>)

enum class PermissionResultType {
    Grant, Rationale, Denied
}