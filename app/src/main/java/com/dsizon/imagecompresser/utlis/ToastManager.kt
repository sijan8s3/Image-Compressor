package com.dsizon.imagecompresser.utlis

import android.content.Context
import android.widget.Toast

class ToastManager(private val context: Context) {
    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
