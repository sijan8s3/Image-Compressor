package com.dsizon.imagecompresser

import android.content.Context
import android.net.Uri
import android.os.Environment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class FileManager(
    private val context: Context,
    ) {
    suspend fun saveImage(
        contentUri: Uri,
        filename: String
    ) {
        withContext(Dispatchers.IO) {
            context.contentResolver
                .openInputStream(contentUri)
                ?.use { inputStream ->
                    context.openFileOutput(filename, Context.MODE_PRIVATE).use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }

                }
        }
    }

    suspend fun saveImageToDownloads(
        byteArray: ByteArray,
        filename: String
    ) {
        withContext(Dispatchers.IO) {
            val downloadsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(downloadsDirectory, filename)

            FileOutputStream(file).use { outputStream ->
                outputStream.write(byteArray)
            }
        }
    }
}