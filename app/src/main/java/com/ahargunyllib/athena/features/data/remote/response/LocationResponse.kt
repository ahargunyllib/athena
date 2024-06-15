package com.ahargunyllib.athena.features.data.remote.response

data class FriendsLocationResponse(
    val statusCode: Int,
    val message: String,
    val data: List<FriendLocationResponse>,
)

data class FriendLocationResponse(
    val userId: String,
    val fullName: String,
    val username: String,
    val latitude: Float,
    val longitude: Float,
)

data class UpdateLocationResponse(
    val statusCode: Int,
    val message: String,
    val data: List<Any>,
)