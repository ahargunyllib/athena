package com.ahargunyllib.athena.features.data.remote.response

data class FriendListResponse(
    val statusCode: Int,
    val message: String,
    val data: List<FriendshipResponse>,
)

data class FriendshipResponse(
    val userId: String,
    val fullName: String,
    val username: String,
    val status: FriendshipStatus,
    val imageUrl: String
)

enum class FriendshipStatus {
    PENDING,
    ACCEPTED,
}

data class MinUserResponse(
    val userId: String,
    val fullName: String,
    val username: String,
    val imageUrl: String
)

data class FriendResponse(
    val user: MinUserResponse,
    val friend: MinUserResponse,
    val status: FriendshipStatus
)

data class AddFriendResponse(
    val statusCode: Int,
    val message: String,
    val data: FriendResponse,
)

data class RemoveFriendResponse(
    val statusCode: Int,
    val message: String,
    val data: List<Any>,
)

data class AcceptFriendResponse(
    val statusCode: Int,
    val message: String,
    val data: List<Any>,
)

data class RejectFriendResponse(
    val statusCode: Int,
    val message: String,
    val data: List<Any>,
)

data class SearchUserResponse(
    val statusCode: Int,
    val message: String,
    val data: List<MinUserResponse>,
)


