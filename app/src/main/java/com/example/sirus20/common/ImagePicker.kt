package com.example.sirus20.common

import android.content.Context
import android.net.Uri
import android.os.Environment
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

}