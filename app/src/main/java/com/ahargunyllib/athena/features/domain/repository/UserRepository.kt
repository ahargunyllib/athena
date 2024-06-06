package com.ahargunyllib.athena.features.domain.repository

import com.ahargunyllib.athena.features.data.local.UserEntity
import com.ahargunyllib.athena.features.data.remote.response.UserResponse
import com.ahargunyllib.athena.features.data.remote.response.UsersResponse
import com.ahargunyllib.athena.features.utils.Response
import kotlinx.coroutines.flow.Flow


interface UserRepository {
    suspend fun getToken(): String?
    suspend fun insertUser(user: UserEntity)
    suspend fun getUser(): UserEntity?
    suspend fun deleteUser()
}