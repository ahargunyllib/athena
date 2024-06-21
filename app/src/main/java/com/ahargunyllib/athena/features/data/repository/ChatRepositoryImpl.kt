package com.ahargunyllib.athena.features.data.repository

import com.ahargunyllib.athena.features.data.remote.API
import com.ahargunyllib.athena.features.data.remote.response.ChatRoomResponse
import com.ahargunyllib.athena.features.data.remote.response.MessagesResponse
import com.ahargunyllib.athena.features.domain.repository.ChatRepository
import com.ahargunyllib.athena.features.domain.repository.UserRepository
import com.ahargunyllib.athena.features.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val api: API,
    private val userRepository: UserRepository
) : ChatRepository {
    override suspend fun getChatRooms(): Flow<Response<ChatRoomResponse>> {
        return flow {
            emit(Response.Loading())

            try {
                val token = userRepository.getToken()

                val response = api.getChatRooms(
                    "Bearer $token"
                )

                when (response.statusCode) {
                    200 -> {
                        emit(Response.Loading(isLoading = false))
                        emit(Response.Success(response))
                        return@flow
                    }

                    else -> {
                        emit(Response.Loading(isLoading = false))
                        emit(Response.Error(response.message))
                        return@flow
                    }
                }
            } catch (e: Exception) {
                emit(Response.Loading(isLoading = false))
                emit(Response.Error(e.message ?: "An error occurred"))
            }

        }
    }

    override suspend fun getMessages(
        chatRoomId: String
    ): Flow<Response<MessagesResponse>> {
        return flow {
            emit(Response.Loading())

            try {
                val token = userRepository.getToken()

                val response = api.getMessages(
                    "Bearer $token",
                    chatRoomId
                )

                when (response.statusCode) {
                    200 -> {
                        emit(Response.Loading(isLoading = false))
                        emit(Response.Success(response))
                        return@flow
                    }

                    else -> {
                        emit(Response.Loading(isLoading = false))
                        emit(Response.Error(response.message))
                        return@flow
                    }
                }
            } catch (e: Exception) {
                emit(Response.Loading(isLoading = false))
                emit(Response.Error(e.message ?: "An error occurred"))
            }
        }
    }
}