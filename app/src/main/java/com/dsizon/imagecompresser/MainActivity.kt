package com.dsizon.imagecompresser

import android.os.Bundle
import android.provider.ContactsContract.Contacts.Photo
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dsizon.imagecompresser.permissions.PermissionManager
import com.dsizon.imagecompresser.ui.theme.ImageCompresserTheme
import com.dsizon.imagecompresser.utlis.ToastManager

class MainActivity : ComponentActivity() {
    private lateinit var toastManager: ToastManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        toastManager = ToastManager(this)

        setContent {
            ImageCompresserTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PhotoPickerScreen(
                        modifier = Modifier.padding(innerPadding),
                        imageCompressor = remember { ImageCompressor(applicationContext) },
                        fileManager = remember { FileManager(applicationContext) },
                        toastManager = toastManager
                    )
                }
            }
        }
    }
}