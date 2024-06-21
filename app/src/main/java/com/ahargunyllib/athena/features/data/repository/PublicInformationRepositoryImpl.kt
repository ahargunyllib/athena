package com.ahargunyllib.athena.features.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.ahargunyllib.athena.features.data.remote.API
import com.ahargunyllib.athena.features.data.remote.response.PublicInformationResponse
import com.ahargunyllib.athena.features.data.remote.response.PublicInformationsResponse
import com.ahargunyllib.athena.features.data.remote.response.UpdateLocationResponse
import com.ahargunyllib.athena.features.domain.model.CreateCommentModel
import com.ahargunyllib.athena.features.domain.model.CreatePublicInformationModel
import com.ahargunyllib.athena.features.domain.model.CreateReportModel
import com.ahargunyllib.athena.features.domain.repository.PublicInformationRepository
import com.ahargunyllib.athena.features.domain.repository.UserRepository
import com.ahargunyllib.athena.features.utils.Response
import com.ahargunyllib.athena.features.utils.prepareFilePart
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class PublicInformationRepositoryImpl @Inject constructor(
    private val api: API,
    private val userRepository: UserRepository
) : PublicInformationRepository {
    override suspend fun getPublicInformation(): Flow<Response<PublicInformationsResponse>> {
        return flow {
            emit(Response.Loading())

            try {
                val token = userRepository.getToken()

                val response = api.getPublicInformation(
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

    override suspend fun getPublicInformationById(
        publicInformationId: String
    ): Flow<Response<PublicInformationResponse>> {
        return flow {
            emit(Response.Loading())

            try {
                val token = userRepository.getToken()

                val response = api.getPublicInformationById(
                    "Bearer $token",
                    publicInformationId
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

    override suspend fun createPublicInformation(
        context: Context,
        createPublicInformationModel: CreatePublicInformationModel
    ): Flow<Response<UpdateLocationResponse>> {
        return flow {
            emit(Response.Loading())

            try {
                val token = userRepository.getToken()
                Log.i("PublicInformationRepositoryImpl.createPublicInformation", "createPublicInformationModel: $createPublicInformationModel")

                val response = api.createPublicInformation(
                    token = "Bearer $token",
                    latitude = createPublicInformationModel.latitude.toString().toRequestBody(),
                    longitude = createPublicInformationModel.longitude.toString().toRequestBody(),
                    content = createPublicInformationModel.content.toRequestBody(),
                    avatar = if (createPublicInformationModel.imageUri != Uri.EMPTY) prepareFilePart(
                        context,
                        createPublicInformationModel.imageUri
                    ) else null
                )

                Log.i("PublicInformationRepositoryImpl.createPublicInformation", "response.message: ${response.message}")

                when (response.statusCode) {
                    200 -> {
                        emit(Response.Loading(isLoading = false))
                        emit(Response.Success(response))
                        return@flow
                    }

                    else -> {
                        Log.i("PublicInformationRepositoryImpl.createPublicInformation", "response.message: ${response.message}")
                        emit(Response.Loading(isLoading = false))
                        emit(Response.Error(response.message))
                        return@flow
                    }
                }
            } catch (e: Exception) {
                Log.i("PublicInformationRepositoryImpl.createPublicInformation", "e.message: ${e.message}")
                emit(Response.Loading(isLoading = false))
                emit(Response.Error(e.message ?: "An error occurred"))
            }
        }
    }

    override suspend fun createComment(
        createCommentModel: CreateCommentModel,
        publicInformationId: String
    ): Flow<Response<UpdateLocationResponse>> {
        return flow {
            emit(Response.Loading())

            try {
                val token = userRepository.getToken()

                val response = api.createComment(
                    "Bearer $token",
                    publicInformationId,
                    createCommentModel
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

    override suspend fun reportPublicInformation(
        createReportModel: CreateReportModel,
        publicInformationId: String
    ): Flow<Response<UpdateLocationResponse>> {
        return flow {
            emit(Response.Loading())

            try {
                val token = userRepository.getToken()

                val response = api.reportPublicInformation(
                    "Bearer $token",
                    publicInformationId,
                    createReportModel
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