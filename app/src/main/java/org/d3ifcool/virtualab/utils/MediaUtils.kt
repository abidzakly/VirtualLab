package org.d3ifcool.virtualab.utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.webkit.MimeTypeMap
import com.canhub.cropper.CropImageView
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream


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

fun Bitmap.toMultipartBody(): MultipartBody.Part {
    val stream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, 30, stream)
    val byteArray = stream.toByteArray()
    val requestBody = byteArray.toRequestBody(
        "image/jpg".toMediaTypeOrNull(), 0, byteArray.size
    )
    return MultipartBody.Part.createFormData("file", "image.jpg", requestBody)
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

fun getCroppedImage(
    resolver: ContentResolver,
    result: CropImageView.CropResult
): Bitmap? {
    if (!result.isSuccessful) {
        Log.e("IMAGE", "Error: ${result.error}")
        return null
    }
    val uri = result.uriContent ?: return null

    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
        MediaStore.Images.Media.getBitmap(resolver, uri)
    } else {
        val source = ImageDecoder.createSource(resolver, uri)
        ImageDecoder.decodeBitmap(source)
    }
}