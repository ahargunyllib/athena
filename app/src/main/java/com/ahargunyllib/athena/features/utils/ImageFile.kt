package com.ahargunyllib.athena.features.utils

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.InputStream
import java.io.OutputStream

fun getFileFromUri(context: Context, uri: Uri): File {
    val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
    val file = File(context.cacheDir, "temp_image.jpg")
    val outputStream: OutputStream = file.outputStream()
    inputStream?.copyTo(outputStream)
    return file
}

fun prepareFilePart(context: Context, uri: Uri): MultipartBody.Part {
    val file = getFileFromUri(context, uri)
    val mediaType = "image/*".toMediaTypeOrNull()
        ?: throw IllegalStateException("Media type cannot be null")
    val requestFile = file.asRequestBody(mediaType)
    return MultipartBody.Part.createFormData("avatar", file.name, requestFile)
}
