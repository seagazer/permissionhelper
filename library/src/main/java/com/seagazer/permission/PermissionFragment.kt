package com.seagazer.permission

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData

/**
 * Author: Seagazer
 * Date: 2020/4/23
 */
class PermissionFragment : Fragment() {
    companion object {
        const val REQUEST_CODE = 0x110
    }

    private var liveData: MutableLiveData<PermissionResult>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onDestroy() {
        super.onDestroy()
        this.liveData = null
    }

    fun requestPermissions(
        observable: MutableLiveData<PermissionResult>,
        permissions: Array<String>
    ) {
        requestPermissions(permissions, REQUEST_CODE)
        this.liveData = observable
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        this.liveData?.let {
            val rationalePermissions: MutableList<String> = mutableListOf()
            val deniedPermissions: MutableList<String> = mutableListOf()
            if (requestCode == REQUEST_CODE) {
                for (index in permissions.indices) {
                    val permission = permissions[index]
                    val result = grantResults[index]
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        if (shouldShowRequestPermissionRationale(permission)) {
                            rationalePermissions.add(permission)
                        } else {
                            deniedPermissions.add(permission)
                        }
                    }
                }
                if (deniedPermissions.isEmpty() && rationalePermissions.isEmpty()) {// grant
                    liveData!!.value =
                        PermissionResult(PermissionResultType.Grant, permissions.toMutableList())
                } else if (rationalePermissions.isNotEmpty()) {// rationale
                    liveData!!.value =
                        PermissionResult(PermissionResultType.Rationale, rationalePermissions)
                } else if (deniedPermissions.isNotEmpty()) {// denied
                    liveData!!.value =
                        PermissionResult(PermissionResultType.Denied, deniedPermissions)
                }
            }
        }
    }


}