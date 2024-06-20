package com.ahargunyllib.athena.features.domain.model

import android.graphics.Bitmap
import android.net.Uri
import okhttp3.MultipartBody


data class RegisterModel(
    val fullName: String,
    val username: String,
    val email: String,
    val password: String,
    val confirmPassword: String,
    val phoneNumber: String,
    val dateOfBirth: String
)

data class LoginModel(
    val email: String,
    val password: String
)

data class UpdateModel(
    val fullName: String,
    val username: String,
    val phoneNumber: String,
    val dateOfBirth: String,
    val imageUri: Uri
)

data class CredentialsModel(
    val email: String,
    val password: String,
    val confirmPassword: String
)