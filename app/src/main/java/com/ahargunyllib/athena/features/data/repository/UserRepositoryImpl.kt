package com.ahargunyllib.athena.features.data.repository

import android.util.Log
import com.ahargunyllib.athena.features.data.local.UserDAO
import com.ahargunyllib.athena.features.data.local.UserDatabase
import com.ahargunyllib.athena.features.data.remote.API
import com.ahargunyllib.athena.features.data.remote.response.UserResponse
import com.ahargunyllib.athena.features.data.remote.response.UsersResponse
import com.ahargunyllib.athena.features.domain.repository.UserRepository
import com.ahargunyllib.athena.features.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.HttpURLConnection.HTTP_OK
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: API,
    private val db: UserDatabase
) : UserRepository {
    override fun getUsers(): Flow<Response<UsersResponse>> {
        return flow {
            emit(Response.Loading(isLoading = true))
            try {
                val response = api.getUsers()
                Log.d("UserRepositoryImpl", "getUsers: $response")

                if (response.status == HTTP_OK) {
                    emit(Response.Success(response))
                    emit(Response.Loading(isLoading = false))
                    return@flow
                } else {
                    emit(Response.Error(response.message))
                    emit(Response.Loading(isLoading = false))
                    return@flow
                }
            } catch (e: Exception) {
                emit(Response.Error(e.message ?: "An error occurred"))
                emit(Response.Loading(isLoading = false))
                Log.d("UserRepositoryImpl", "getUsers: ${e.message}")
            }
        }
    }

    override fun getUserById(id: Int): Flow<Response<UserResponse>> {
        TODO("Not yet implemented")
    }

    override fun insertUser() {
        TODO("Not yet implemented")
    }

    override fun deleteUser() {
        TODO("Not yet implemented")
    }
}