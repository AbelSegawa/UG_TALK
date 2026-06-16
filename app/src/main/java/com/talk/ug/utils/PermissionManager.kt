package com.talk.ug.utils

import android.app.Activity
import android.content.Context
import pub.devrel.easypermissions.EasyPermissions

class PermissionManager(private val context: Context) {

    fun hasPermissions(vararg permissions: String): Boolean {
        return EasyPermissions.hasPermissions(context, *permissions)
    }

    fun requestPermissions(
        activity: Activity,
        requestCode: Int,
        vararg permissions: String
    ) {
        EasyPermissions.requestPermissions(
            activity,
            "This app needs permissions to function properly",
            requestCode,
            *permissions
        )
    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 100
        const val CAMERA_PERMISSION = android.Manifest.permission.CAMERA
        const val RECORD_AUDIO_PERMISSION = android.Manifest.permission.RECORD_AUDIO
        const val READ_STORAGE_PERMISSION = android.Manifest.permission.READ_EXTERNAL_STORAGE
        const val WRITE_STORAGE_PERMISSION = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    }
}
