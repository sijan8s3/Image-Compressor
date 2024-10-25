package com.dsizon.imagecompresser.permissions

import android.Manifest
import android.content.Context
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker

class PermissionManager(private val context: Context) {

    fun checkStoragePermission(): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_GRANTED
    }

    fun requestStoragePermission(
        permissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
        onPermissionDenied: () -> Unit
    ) {
        permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (!checkStoragePermission()) {
            onPermissionDenied()
        }
    }
}
