package com.ahargunyllib.athena.features.domain.repository

import com.ahargunyllib.athena.features.data.remote.response.ChatRoomResponse
import com.ahargunyllib.athena.features.data.remote.response.MessagesResponse
import com.ahargunyllib.athena.features.utils.Response
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun getChatRooms(): Flow<Response<ChatRoomResponse>>
    suspend fun getMessages(chatRoomId: String): Flow<Response<MessagesResponse>>
}