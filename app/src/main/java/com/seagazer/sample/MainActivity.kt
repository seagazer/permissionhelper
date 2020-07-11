package com.seagazer.sample

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.seagazer.permission.PermissionHelper
import com.seagazer.permission.PermissionResultType

class MainActivity : AppCompatActivity() {

    private val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun check(view: View) {
        PermissionHelper(this).apply {
            if (!hasPermissions(permissions)) {
                requestPermissions(permissions).observe(this@MainActivity, Observer {
                    when (it.type) {
                        PermissionResultType.Grant -> {
                            toastShort("Permission Grant: ${it.permissions}")
                        }
                        PermissionResultType.Denied -> {
                            toastShort("Permission Denied: ${it.permissions}")
                        }
                        PermissionResultType.Rationale -> {
                            toastShort("Permission Rationale: ${it.permissions}")
                        }
                    }
                })
            } else {
                toastShort("Permission has granted!")
            }
        }
    }
}
