package com.ahargunyllib.athena.features.data.repository

import android.util.Log
import com.ahargunyllib.athena.features.data.remote.API
import com.ahargunyllib.athena.features.data.remote.response.FriendsLocationResponse
import com.ahargunyllib.athena.features.data.remote.response.UpdateLocationResponse
import com.ahargunyllib.athena.features.domain.model.LocationModel
import com.ahargunyllib.athena.features.domain.repository.LocationRepository
import com.ahargunyllib.athena.features.domain.repository.UserRepository
import com.ahargunyllib.athena.features.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class LocationRepositoryImpl @Inject constructor(
    private val api: API,
    private val userRepository: UserRepository
) : LocationRepository {
    override suspend fun getFriendsLocation(): Flow<Response<FriendsLocationResponse>> {
        return flow {
            emit(Response.Loading())

            try {
                val token = userRepository.getToken()

                val response = api.getFriendsLocation("Bearer $token")

                Log.i("LocationRepositoryImpl.getFriendsLocation", "getFriendsLocation: $response")

                when (response.statusCode) {
                    200 -> {
                        emit(Response.Loading(isLoading = false))
                        emit(Response.Success(response))
                        return@flow
                    }
                    else -> {
                        emit(Response.Loading(isLoading = false))
                        emit(Response.Error(response.message))

                        Log.i("LocationRepositoryImpl.getFriendsLocation", "else: ${response.message}")
                        return@flow
                    }
                }
            } catch (e: Exception) {
                emit(Response.Loading(isLoading = false))
                emit(Response.Error(e.message ?: "An error occurred"))

                Log.i("LocationRepositoryImpl.getFriendsLocation", "catch: ${e.message}")
                return@flow
            }
        }
    }

    override suspend fun updateLocation(
        request: LocationModel
    ): Flow<Response<UpdateLocationResponse>> {
        return flow {
            emit(Response.Loading())

            try {
                val token = userRepository.getToken()

                val response = api.updateLocation("Bearer $token", request)

                Log.i("LocationRepositoryImpl.updateLocation", "updateLocation: $response")

                when (response.statusCode) {
                    200 -> {
                        emit(Response.Loading(isLoading = false))
                        emit(Response.Success(response))
                        return@flow
                    }
                    else -> {
                        emit(Response.Loading(isLoading = false))
                        emit(Response.Error(response.message))

                        Log.i("LocationRepositoryImpl.updateLocation", "else: ${response.message}")
                        return@flow
                    }
                }
            } catch (e: Exception) {
                emit(Response.Loading(isLoading = false))
                emit(Response.Error(e.message ?: "An error occurred"))

                Log.i("LocationRepositoryImpl.updateLocation", "catch: ${e.message}")
                return@flow
            }
        }
    }

    override suspend fun sos(): Flow<Response<UpdateLocationResponse>> {
        return flow {
            emit(Response.Loading())

            try {
                val token = userRepository.getToken()

                val response = api.sos("Bearer $token")

                Log.i("LocationRepositoryImpl.sos", "sos: $response")

                when (response.statusCode) {
                    200 -> {
                        emit(Response.Loading(isLoading = false))
                        emit(Response.Success(response))
                        return@flow
                    }
                    else -> {
                        emit(Response.Loading(isLoading = false))
                        emit(Response.Error(response.message))

                        Log.i("LocationRepositoryImpl.sos", "else: ${response.message}")
                        return@flow
                    }
                }
            } catch (e: Exception) {
                emit(Response.Loading(isLoading = false))
                emit(Response.Error(e.message ?: "An error occurred"))

                Log.i("LocationRepositoryImpl.sos", "catch: ${e.message}")
                return@flow
            }
        }
    }
}