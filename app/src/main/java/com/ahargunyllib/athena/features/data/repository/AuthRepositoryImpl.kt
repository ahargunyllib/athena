package com.ahargunyllib.athena.features.data.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.ahargunyllib.athena.features.data.local.UserDatabase
import com.ahargunyllib.athena.features.data.local.UserEntity
import com.ahargunyllib.athena.features.data.remote.API
import com.ahargunyllib.athena.features.data.remote.response.LoginResponse
import com.ahargunyllib.athena.features.data.remote.response.RefreshTokenResponse
import com.ahargunyllib.athena.features.data.remote.response.RegisterResponse
import com.ahargunyllib.athena.features.domain.model.LoginModel
import com.ahargunyllib.athena.features.domain.model.RegisterModel
import com.ahargunyllib.athena.features.domain.repository.AuthRepository
import com.ahargunyllib.athena.features.domain.repository.UserRepository
import com.ahargunyllib.athena.features.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: API,
    private val userRepository: UserRepository,
) : AuthRepository {
    override suspend fun register(
        context: Context,
        request: RegisterModel
    ): Flow<Response<RegisterResponse>> {
        Log.i("AuthRepositoryImpl.register", "request: $request")

        return flow {
            emit(Response.Loading())

            try {
                val response = api.register(
                    request
                )

                Log.i("AuthRepositoryImpl.register", "response: $response")

                when (response.statusCode) {
                    201 -> {
                        emit(Response.Success(response))
                        emit(Response.Loading(isLoading = false))
                        return@flow
                    }

                    400 -> {
                        emit(Response.Loading(isLoading = false))
                        emit(Response.Error(response.message))

                        Log.i("AuthRepositoryImpl.register", "400: ${response.message}")
                        return@flow
                    }

                    else -> {
                        emit(Response.Loading(isLoading = false))
                        emit(Response.Error(response.message))

                        Log.i("AuthRepositoryImpl.register", "unknown: ${response.message}")
                        return@flow
                    }
                }
            } catch (e: Exception) {
                emit(Response.Loading(isLoading = false))
                emit(Response.Error(e.message.toString()))
                Log.i("AuthRepositoryImpl.register", "catch: ${e.message.toString()}")
                return@flow
            }
        }
    }

    override suspend fun login(
        context: Context,
        request: LoginModel
    ): Flow<Response<LoginResponse>> {
        Log.i("AuthRepositoryImpl.login", "request: $request")

        return flow {
            emit(Response.Loading())

            try {
                val response = api.login(
                    request
                )

                Log.i("AuthRepositoryImpl.login", "response: $response")

                when (response.statusCode) {
                    200 -> {
                        emit(Response.Success(response))
                        emit(Response.Loading(isLoading = false))

                        val user = UserEntity(
                            userId = response.data.user.userId,
                            fullName = response.data.user.fullName,
                            username = response.data.user.username,
                            email = response.data.user.email,
                            phoneNumber = response.data.user.phoneNumber,
                            dateOfBirth = response.data.user.dateOfBirth,
                            createdAt = response.data.user.createdAt,
                            updatedAt = response.data.user.updatedAt,
                            token = response.data.token,
                            isSharingLocation = true,
                            isPauseAll = false,
                            isShowNotification = true,
                            imageUrl = response.data.user.imageUrl
                        )
                        userRepository.insertUser(user)

                        return@flow
                    }

                    400 -> {
                        emit(Response.Loading(isLoading = false))
                        emit(Response.Error(response.message))

                        Log.i("AuthRepositoryImpl.login", "400: ${response.message}")
                        return@flow
                    }

                    404 -> {
                        emit(Response.Loading(isLoading = false))
                        emit(Response.Error(response.message))

                        Log.i("AuthRepositoryImpl.login", "404: ${response.message}")
                        return@flow
                    }

                    else -> {
                        emit(Response.Loading(isLoading = false))
                        emit(Response.Error(response.message))

                        Log.i("AuthRepositoryImpl.login", "unknown: ${response.message}")
                        return@flow
                    }
                }
            } catch (e: Exception) {
                emit(Response.Loading(isLoading = false))
                emit(Response.Error(e.message.toString()))
                Log.i("AuthRepositoryImpl.login", "catch: ${e.message.toString()}")
                return@flow
            }
        }
    }

    override suspend fun refreshToken(token: String): Flow<Response<RefreshTokenResponse>> {
        return flow {
            emit(Response.Loading())

            try {
                val response = api.refreshToken("Bearer $token")

                Log.i("AuthRepositoryImpl.refreshToken", "response: $response")

                when (response.statusCode) {
                    200 -> {
                        emit(Response.Success(response))
                        emit(Response.Loading(isLoading = false))

                        val user = userRepository.getUser()
                        if (user != null) {
                            val updatedUser = user.copy(token = response.data)
                            userRepository.deleteUser()
                            userRepository.insertUser(updatedUser)
                        }

                        return@flow
                    }

                    400 -> {
                        emit(Response.Loading(isLoading = false))
                        emit(Response.Error(response.message))

                        Log.i("AuthRepositoryImpl.refreshToken", "400: ${response.message}")
                        return@flow
                    }

                    else -> {
                        emit(Response.Loading(isLoading = false))
                        emit(Response.Error(response.message))

                        Log.i("AuthRepositoryImpl.refreshToken", "unknown: ${response.message}")
                        return@flow
                    }
                }
            } catch (e: Exception) {
                emit(Response.Loading(isLoading = false))
                emit(Response.Error(e.message.toString()))
                Log.i("AuthRepositoryImpl.refreshToken", "catch: ${e.message.toString()}")
                return@flow
            }
        }
    }

}