package com.ahargunyllib.athena.features.data.repository

import android.content.Context
import android.util.Log
import com.ahargunyllib.athena.features.data.remote.API
import com.ahargunyllib.athena.features.data.remote.response.AcceptFriendResponse
import com.ahargunyllib.athena.features.data.remote.response.AddFriendResponse
import com.ahargunyllib.athena.features.data.remote.response.FriendListResponse
import com.ahargunyllib.athena.features.data.remote.response.RejectFriendResponse
import com.ahargunyllib.athena.features.data.remote.response.RemoveFriendResponse
import com.ahargunyllib.athena.features.data.remote.response.SearchUserResponse
import com.ahargunyllib.athena.features.domain.repository.FriendshipRepository
import com.ahargunyllib.athena.features.domain.repository.UserRepository
import com.ahargunyllib.athena.features.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FriendshipRepositoryImpl @Inject constructor(
    private val api : API,
    private val userRepository: UserRepository
): FriendshipRepository {
    override suspend fun getFriendList(
        context: Context,
    ): Flow<Response<FriendListResponse>> {
        Log.i("FriendshipRepositoryImpl.getFriendList", "")

        return flow {
            emit(Response.Loading())

            try {
                val token = userRepository.getToken()

                val response = api.getFriendList(
                    "Bearer $token"
                )

                Log.i("FriendshipRepositoryImpl.getFriendList", "response: $response")

                when (response.statusCode) {
                    200 -> {
                        emit(Response.Loading(isLoading = false))
                        emit(Response.Success(response))
                        return@flow
                    }

                    else -> {
                        emit(Response.Loading(isLoading = false))
                        emit(Response.Error(response.message))

                        Log.i("FriendshipRepositoryImpl.getFriendList", "else: ${response.message}")
                        return@flow
                    }
                }
            } catch (e: Exception) {
                emit(Response.Loading(isLoading = false))
                emit(Response.Error(e.message ?: "An error occurred"))

                Log.e("FriendshipRepositoryImpl.getFriendList", "Exception: $e")
                return@flow
            }
        }
    }

    override suspend fun addFriendRequest(
        context: Context,
        userId: String
    ): Flow<Response<AddFriendResponse>> {
        Log.i("FriendshipRepositoryImpl.addFriendRequest", "")

        return flow {
            emit(Response.Loading())

            try {
                val token = userRepository.getToken()

                val response = api.addFriend(
                    "Bearer $token",
                    userId
                )

                Log.i("FriendshipRepositoryImpl.addFriendRequest", "response: $response")

                when (response.statusCode) {
                    201 -> {
                        emit(Response.Loading(isLoading = false))
                        emit(Response.Success(response))
                        return@flow
                    }

                    400 -> {
                        emit(Response.Loading(isLoading = false))
                        emit(Response.Error(response.message))

                        Log.i("FriendshipRepositoryImpl.addFriendRequest", "400: ${response.message}")
                        return@flow
                    }

                    else -> {
                        emit(Response.Loading(isLoading = false))
                        emit(Response.Error(response.message))

                        Log.i("FriendshipRepositoryImpl.addFriendRequest", "else: ${response.message}")
                        return@flow
                    }
                }
            } catch (e: Exception) {
                emit(Response.Loading(isLoading = false))
                emit(Response.Error(e.message ?: "An error occurred"))

                Log.e("FriendshipRepositoryImpl.addFriendRequest", "Exception: $e")
                return@flow
            }
        }
    }

    override suspend fun removeFriend(
        context: Context,
        userId: String
    ): Flow<Response<RemoveFriendResponse>> {
        Log.i("FriendshipRepositoryImpl.removeFriend", "")

        return flow {
            emit(Response.Loading())

            try {
                val token = userRepository.getToken()

                val response = api.removeFriend(
                    "Bearer $token",
                    userId
                )

                Log.i("FriendshipRepositoryImpl.removeFriend", "response: $response")

                when (response.statusCode) {
                    200 -> {
                        emit(Response.Loading(isLoading = false))
                        emit(Response.Success(response))
                        return@flow
                    }

                    else -> {
                        emit(Response.Loading(isLoading = false))
                        emit(Response.Error(response.message))

                        Log.i("FriendshipRepositoryImpl.removeFriend", "else: ${response.message}")
                        return@flow
                    }
                }
            } catch (e: Exception) {
                emit(Response.Loading(isLoading = false))
                emit(Response.Error(e.message ?: "An error occurred"))

                Log.e("FriendshipRepositoryImpl.removeFriend", "Exception: $e")
                return@flow
            }
        }
    }

    override suspend fun acceptFriend(
        context: Context,
        userId: String
    ): Flow<Response<AcceptFriendResponse>> {
        Log.i("FriendshipRepositoryImpl.acceptFriend", "")

        return flow {
            emit(Response.Loading())

            try {
                val token = userRepository.getToken()

                val response = api.acceptFriend(
                    "Bearer $token",
                    userId
                )

                Log.i("FriendshipRepositoryImpl.acceptFriend", "response: $response")

                when (response.statusCode) {
                    200 -> {
                        emit(Response.Loading(isLoading = false))
                        emit(Response.Success(response))
                        return@flow
                    }

                    else -> {
                        emit(Response.Loading(isLoading = false))
                        emit(Response.Error(response.message))

                        Log.i("FriendshipRepositoryImpl.acceptFriend", "else: ${response.message}")
                        return@flow
                    }
                }
            } catch (e: Exception) {
                emit(Response.Loading(isLoading = false))
                emit(Response.Error(e.message ?: "An error occurred"))

                Log.e("FriendshipRepositoryImpl.acceptFriend", "Exception: $e")
                return@flow
            }
        }
    }

    override suspend fun rejectFriend(
        context: Context,
        userId: String
    ): Flow<Response<RejectFriendResponse>> {
        Log.i("FriendshipRepositoryImpl.rejectFriend", "")

        return flow {
            emit(Response.Loading())

            try {
                val token = userRepository.getToken()

                val response = api.rejectFriend(
                    "Bearer $token",
                    userId
                )

                Log.i("FriendshipRepositoryImpl.rejectFriend", "response: $response")

                when (response.statusCode) {
                    200 -> {
                        emit(Response.Loading(isLoading = false))
                        emit(Response.Success(response))
                        return@flow
                    }

                    else -> {
                        emit(Response.Loading(isLoading = false))
                        emit(Response.Error(response.message))

                        Log.i("FriendshipRepositoryImpl.rejectFriend", "else: ${response.message}")
                        return@flow
                    }
                }
            } catch (e: Exception) {
                emit(Response.Loading(isLoading = false))
                emit(Response.Error(e.message ?: "An error occurred"))

                Log.e("FriendshipRepositoryImpl.rejectFriend", "Exception: $e")
                return@flow
            }
        }
    }

    override suspend fun searchUser(
        context: Context,
        username: String
    ): Flow<Response<SearchUserResponse>> {
        Log.i("FriendshipRepositoryImpl.searchUser", "")

        return flow {
            emit(Response.Loading())

            try {
                val token = userRepository.getToken()

                val response = api.searchUser(
                    "Bearer $token",
                    username = username
                )

                Log.i("FriendshipRepositoryImpl.searchUser", "response: $response")

                when (response.statusCode) {
                    200 -> {
                        emit(Response.Loading(isLoading = false))
                        emit(Response.Success(response))
                        return@flow
                    }

                    else -> {
                        emit(Response.Loading(isLoading = false))
                        emit(Response.Error(response.message))

                        Log.i("FriendshipRepositoryImpl.searchUser", "else: ${response.message}")
                        return@flow
                    }
                }
            } catch (e: Exception) {
                emit(Response.Loading(isLoading = false))
                emit(Response.Error(e.message ?: "An error occurred"))

                Log.e("FriendshipRepositoryImpl.searchUser", "Exception: $e")
                return@flow
            }
        }
    }
}