package org.d3ifcool.virtualab.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns


fun isImage(uri: Uri, contentResolver: ContentResolver): Boolean {
    val mimeType = contentResolver.getType(uri)
    return when (mimeType) {
        "image/jpeg" -> true
        "image/jpg" -> true
        "image/png" -> true
        else -> false
    }
}

fun getFileExtension(uri: Uri, contentResolver: ContentResolver): String? {
    val mimeType = contentResolver.getType(uri)
    return when (mimeType) {
        "image/jpeg" -> "jpeg"
        "image/jpg" -> "jpg"
        "image/png" -> "png"
        "video/mp4" -> "mp4"
        "video/mpeg" -> "mpeg"
        "video/quicktime" -> "mov"
        "video/webm" -> "webm"
        "video/ogg" -> "ogv"
        else -> null
    }
}

fun getFileName(context: Context, uri: Uri): String {
    var fileName = ""
    context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)

        if (cursor.moveToFirst()) {
            fileName = cursor.getString(nameIndex)
        }
    }
    return fileName
}