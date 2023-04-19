package com.example.sirus20.common

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.OpenableColumns
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

object ImagePicker {

    /*
  * getting url from gallery
  * */

    fun getFileFromUri(uri: Uri, context: Context): File {
        val storageDir: File? =
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file =
            File.createTempFile("Img_", ".png", storageDir)
        file.outputStream().use {
            val inputStream = context.contentResolver.openInputStream(uri)
            inputStream?.copyTo(it)
            inputStream?.close()
        }

        return file
    }

    @SuppressLint("Range")
     fun getFileName(context: Context, uri: Uri): String? {
        if (uri.scheme == "content") {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor.use {
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        return cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    }
                }
            }
        }
        return uri.path?.lastIndexOf('/')?.let { uri.path?.substring(it) }
    }
}