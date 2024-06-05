package com.ahargunyllib.athena.features.domain.model


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