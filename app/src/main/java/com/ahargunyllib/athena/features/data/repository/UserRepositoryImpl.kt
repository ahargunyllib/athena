package com.ahargunyllib.athena.features.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.ahargunyllib.athena.features.data.local.UserDAO
import com.ahargunyllib.athena.features.data.local.UserDatabase
import com.ahargunyllib.athena.features.data.local.UserEntity
import com.ahargunyllib.athena.features.data.remote.API
import com.ahargunyllib.athena.features.data.remote.response.ProfileUserResponse
import com.ahargunyllib.athena.features.data.remote.response.RegisterResponse
import com.ahargunyllib.athena.features.data.remote.response.UserResponse
import com.ahargunyllib.athena.features.data.remote.response.UsersResponse
import com.ahargunyllib.athena.features.domain.model.UpdateModel
import com.ahargunyllib.athena.features.domain.repository.UserRepository
import com.ahargunyllib.athena.features.utils.Response
import com.ahargunyllib.athena.features.utils.prepareFilePart
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.net.HttpURLConnection.HTTP_OK
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val db: UserDatabase,
    private val api: API
) : UserRepository {
    override suspend fun getToken(): String? {
        try {
            val userDAO = db.getUserDAO()
            val user = userDAO.getUser()
            return user.token
        } catch (e: Exception) {
            Log.e("UserRepositoryImpl", "getToken: ${e.message}")
            return null
        }
    }

    override suspend fun insertUser(user: UserEntity) {
        try {
            val userDAO = db.getUserDAO()
            userDAO.upsert(user)
        } catch (e: Exception) {
            Log.e("UserRepositoryImpl", "insertUser: ${e.message}")
        }
    }

    override suspend fun getUser(): UserEntity? {
        try {
            val userDAO = db.getUserDAO()
            return userDAO.getUser()
        } catch (e: Exception) {
            Log.e("UserRepositoryImpl", "getUser: ${e.message}")
            return null
        }
    }

    override suspend fun deleteUser() {
        try {
            val userDAO = db.getUserDAO()
            userDAO.deleteUser()
        } catch (e: Exception) {
            Log.e("UserRepositoryImpl", "deleteUser: ${e.message}")
        }
    }

    override suspend fun updateIsSharingLocation(userId: String, isSharingLocation: Boolean) {
        try {
            val userDAO = db.getUserDAO()
            userDAO.updateIsSharingLocation(userId, isSharingLocation)
        } catch (e: Exception) {
            Log.e("UserRepositoryImpl", "updateIsSharingLocation: ${e.message}")
        }
    }

    override suspend fun updateIsPauseAll(userId: String, isPauseAll: Boolean) {
        try {
            val userDAO = db.getUserDAO()
            userDAO.updateIsPauseAll(userId, isPauseAll)
        } catch (e: Exception) {
            Log.e("UserRepositoryImpl", "updateIsPauseAll: ${e.message}")
        }
    }

    override suspend fun updateIsShowNotification(userId: String, isShowNotification: Boolean) {
        try {
            val userDAO = db.getUserDAO()
            userDAO.updateIsShowNotification(userId, isShowNotification)
        } catch (e: Exception) {
            Log.e("UserRepositoryImpl", "updateIsShowNotification: ${e.message}")
        }
    }

    override suspend fun getUser(
        context: Context,
        userId: String
    ): Flow<Response<ProfileUserResponse>> {
        return flow {
            emit(Response.Loading())

            try {
                val token = getToken()

                val response = api.getUser(
                    "Bearer $token",
                    userId
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

    override suspend fun updateUser(
        context: Context,
        updateModel: UpdateModel
    ): Flow<Response<RegisterResponse>> {
        return flow {
            emit(Response.Loading())

            try {
                val token = getToken()

                val response = api.updateUser(
                    token = "Bearer $token",
                    fullName = updateModel.fullName.toRequestBody(),
                    username = updateModel.username.toRequestBody(),
                    phoneNumber = updateModel.phoneNumber.toRequestBody(),
                    dateOfBirth = updateModel.dateOfBirth.toRequestBody(),
                    avatar = if (updateModel.imageUri != Uri.EMPTY) prepareFilePart(
                        context,
                        updateModel.imageUri
                    ) else null
                )

                Log.i("UserRepositoryImpl", "updateUser: ${response.message}")

                when (response.statusCode) {
                    200 -> {
                        emit(Response.Loading(isLoading = false))
                        emit(Response.Success(response))

                        val userDAO = db.getUserDAO()
                        val user = userDAO.getUser()

                        userDAO.deleteUser()
                        userDAO.upsert(
                            UserEntity(
                                user.userId ?: "",
                                response.data?.fullName ?: "",
                                response.data?.username ?: "",
                                user.email ?: "",
                                response.data?.phoneNumber ?: "",
                                response.data?.dateOfBirth ?: "",
                                user.token ?: "",
                                user.isSharingLocation ?: false,
                                user.isPauseAll ?: false,
                                user.isShowNotification ?: false,
                                response.data?.imageUrl ?: "",
                                user.createdAt ?: "",
                                user.updatedAt ?: ""
                            )
                        )
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