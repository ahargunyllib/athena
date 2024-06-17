package com.ahargunyllib.athena.features.data.remote.response

data class ChatRoomResponse(
    val statusCode: Int,
    val message: String,
    val data: List<ChatRoom>
)

data class ChatRoom(
    val chatRoomId: String,
    val friendshipId: String,
    val friend: MinUserResponse,
)

data class MessagesResponse(
    val statusCode: Int,
    val message: String,
    val data: List<Message>
)

data class Message(
    val messageId: String,
    val chatRoomId: String,
    val senderId: String,
    val content: String,
    val type: String,
    val createdAt: String,
    val sender: MinUserResponse
)
