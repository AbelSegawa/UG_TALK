package com.talk.ug.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FileManager(private val context: Context) {

    fun createImageFile(): File {
        val imageDir = File(context.cacheDir, "images")
        if (!imageDir.exists()) {
            imageDir.mkdirs()
        }
        
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        return File(imageDir, "image_$timestamp.jpg")
    }

    fun getImageUri(file: File): Uri {
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }

    fun saveBitmapToFile(bitmap: Bitmap): File {
        val file = createImageFile()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, file.outputStream())
        return file
    }

    fun deleteFile(file: File) {
        if (file.exists()) {
            file.delete()
        }
    }
}
