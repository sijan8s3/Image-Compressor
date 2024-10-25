package com.dsizon.imagecompresser

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import kotlin.math.roundToInt

class ImageCompressor(
    private val context: Context,
) {
    suspend fun compressImage(
        contentUri: Uri,
        //compression threshold in bytes
        //keep compressing until the file is smaller than this threshold
        compressionThreshold: Long,
    ): ByteArray? {
        return withContext(Dispatchers.IO) {
            val mimeType = context.contentResolver.getType(contentUri)
            val inputBytes = context
                .contentResolver
                .openInputStream(contentUri)
                ?.use { inputStream ->
                    inputStream.readBytes()
                } ?: return@withContext null

            ensureActive()
            withContext(Dispatchers.Default) {

                val bitmap = BitmapFactory.decodeByteArray(inputBytes, 0, inputBytes.size)
                ensureActive()
                val compressFormat = when (mimeType) {
                    "image/jpeg" -> Bitmap.CompressFormat.JPEG
                    "image/png" -> Bitmap.CompressFormat.PNG
                    "image/webp" -> if (Build.VERSION.SDK_INT >= 30) {
                        Bitmap.CompressFormat.WEBP_LOSSLESS
                    } else Bitmap.CompressFormat.WEBP

                    else -> Bitmap.CompressFormat.JPEG
                }
                var outputBytes: ByteArray
                var quality = 100


                do {
                    ByteArrayOutputStream().use { outputStream ->

                        bitmap.compress(compressFormat, quality, outputStream)
                        outputBytes = outputStream.toByteArray()
                        quality -= (quality * 0.1).roundToInt()
                    }
                } while (
                    isActive && outputBytes.size > compressionThreshold && quality > 5 &&
                    compressFormat != Bitmap.CompressFormat.PNG
                )
                outputBytes

            }
        }

    }
}