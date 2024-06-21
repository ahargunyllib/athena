package com.ahargunyllib.athena.features.domain.repository

import com.ahargunyllib.athena.features.data.remote.response.LoginResponse
import com.ahargunyllib.athena.features.data.remote.response.RefreshTokenResponse
import com.ahargunyllib.athena.features.data.remote.response.RegisterResponse
import com.ahargunyllib.athena.features.domain.model.LoginModel
import com.ahargunyllib.athena.features.domain.model.RegisterModel
import com.ahargunyllib.athena.features.utils.Response
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun register(request: RegisterModel): Flow<Response<RegisterResponse>>
    suspend fun login(request: LoginModel): Flow<Response<LoginResponse>>
    suspend fun refreshToken(token: String): Flow<Response<RefreshTokenResponse>>
}