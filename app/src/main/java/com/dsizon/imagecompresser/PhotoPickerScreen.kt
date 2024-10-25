package com.dsizon.imagecompresser

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.dsizon.imagecompresser.permissions.PermissionManager
import com.dsizon.imagecompresser.utlis.ToastManager
import kotlinx.coroutines.launch

@Composable
fun PhotoPickerScreen(
    modifier: Modifier = Modifier,
    imageCompressor: ImageCompressor,
    fileManager: FileManager,
    toastManager: ToastManager
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var compressedImageByteArray by remember { mutableStateOf<ByteArray?>(null) }
    var isCompressing by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        selectedImageUri = uri
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (selectedImageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(selectedImageUri),
                    contentDescription = "Selected Image",
                    modifier = Modifier.size(200.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        isCompressing = true
                        scope.launch {
                            val uri = selectedImageUri ?: return@launch
                            val mimeType = context.contentResolver.getType(uri)
                            val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)

                            val originalFilename = getFileNameFromUri(context, uri)
                            val baseFilename = originalFilename.substringBeforeLast(".")
                            val compressedFilename = "${baseFilename}_compressed.$extension"

                            compressedImageByteArray = imageCompressor.compressImage(
                                contentUri = uri,
                                compressionThreshold = 200 * 1024L
                            )

                            if (compressedImageByteArray != null) {
                                fileManager.saveImageToDownloads(
                                    byteArray = compressedImageByteArray!!,
                                    filename = compressedFilename
                                )
                                toastManager.showToast("Compressed successfully! Image saved as $compressedFilename.")
                            } else {
                                toastManager.showToast("Compression failed.")
                            }

                            isCompressing = false
                        }
                    },
                    enabled = !isCompressing
                ) {
                    if (isCompressing) {
                        Text(text = "Compressing...")
                    } else {
                        Text(text = "Compress Image")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                photoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }) {
                Text(text = "Pick Image")
            }
        }
    }
}


/**
 * Helper function to extract the original file name from the URI.
 */
private fun getFileNameFromUri(context: Context, uri: Uri): String {
    var fileName = "unknown"
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (nameIndex != -1) {
                fileName = it.getString(nameIndex)
            }
        }
    }
    return fileName
}