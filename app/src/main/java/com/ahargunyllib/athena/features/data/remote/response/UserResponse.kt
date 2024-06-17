package com.ahargunyllib.athena.features.data.remote.response

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("fullName")
    val name: String,
    @SerializedName("email")
    val email: String,
)

data class UsersResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<User>
)

data class UserResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: User
)

data class ProfileUserResponse(
    val statusCode: Int,
    val message: String,
    val data: ProfileUser
)

data class ProfileUser(
    val userId: String,
    val username: String,
    val fullName: String,
    val email: String,
)