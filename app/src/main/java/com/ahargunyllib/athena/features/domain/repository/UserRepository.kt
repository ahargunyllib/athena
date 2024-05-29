package com.ahargunyllib.athena.features.domain.repository

import com.ahargunyllib.athena.features.data.remote.response.UserResponse
import com.ahargunyllib.athena.features.data.remote.response.UsersResponse
import com.ahargunyllib.athena.features.utils.Response
import kotlinx.coroutines.flow.Flow


interface UserRepository {
    fun getUsers(): Flow<Response<UsersResponse>>
    fun getUserById(id: Int) : Flow<Response<UserResponse>>
    fun insertUser()
    fun deleteUser()
}