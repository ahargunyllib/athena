package com.ahargunyllib.athena.features.data.remote

import com.ahargunyllib.athena.features.data.remote.response.LoginResponse
import com.ahargunyllib.athena.features.data.remote.response.RefreshTokenResponse
import com.ahargunyllib.athena.features.data.remote.response.RegisterResponse
import com.ahargunyllib.athena.features.data.remote.response.UsersResponse
import com.ahargunyllib.athena.features.domain.model.LoginModel
import com.ahargunyllib.athena.features.domain.model.RegisterModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface API {
    @POST("/api/auth/register")
    suspend fun register(
        @Body registerModel: RegisterModel
    ): RegisterResponse

    @POST("/api/auth/login")
    suspend fun login(
        @Body loginModel: LoginModel
    ): LoginResponse

    @POST("/api/auth/refresh-token")
    suspend fun refreshToken(
        @Header ("Authorization") token: String
    ): RefreshTokenResponse
}