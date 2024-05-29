package com.ahargunyllib.athena.features.data.remote

import com.ahargunyllib.athena.features.data.remote.response.UsersResponse
import retrofit2.http.GET

interface API {
    @GET("users")
    suspend fun getUsers() : UsersResponse

}