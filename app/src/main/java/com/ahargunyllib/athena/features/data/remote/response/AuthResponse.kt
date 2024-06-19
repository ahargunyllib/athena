package com.ahargunyllib.athena.features.data.remote.response

data class RegisterResponse (
    val statusCode: Int,
    val message: String,
    val data: UserRegisterResponse,
)

data class LoginResponse (
    val statusCode: Int,
    val message: String,
    val data: UserLoginResponse,
)

data class RefreshTokenResponse (
    val statusCode: Int,
    val message: String,
    val data: String,
)

data class UserRegisterResponse (
    val userId: String,
    val fullName: String,
    val username: String,
    val email: String,
    val phoneNumber: String,
    val dateOfBirth: String,
    val imageUrl: String,
    val createdAt: String,
    val updatedAt: String,
)

data class UserLoginResponse (
    val token: String,
    val user: UserRegisterResponse,
)
