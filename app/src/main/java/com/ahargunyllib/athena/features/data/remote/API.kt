package com.ahargunyllib.athena.features.data.remote

import com.ahargunyllib.athena.features.data.remote.response.AcceptFriendResponse
import com.ahargunyllib.athena.features.data.remote.response.AddFriendResponse
import com.ahargunyllib.athena.features.data.remote.response.FriendListResponse
import com.ahargunyllib.athena.features.data.remote.response.LoginResponse
import com.ahargunyllib.athena.features.data.remote.response.RefreshTokenResponse
import com.ahargunyllib.athena.features.data.remote.response.RegisterResponse
import com.ahargunyllib.athena.features.data.remote.response.RejectFriendResponse
import com.ahargunyllib.athena.features.data.remote.response.RemoveFriendResponse
import com.ahargunyllib.athena.features.data.remote.response.SearchUserResponse
import com.ahargunyllib.athena.features.data.remote.response.UsersResponse
import com.ahargunyllib.athena.features.domain.model.LoginModel
import com.ahargunyllib.athena.features.domain.model.RegisterModel
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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
        @Header("Authorization") token: String
    ): RefreshTokenResponse

    @GET("/api/friendship/list")
    suspend fun getFriendList(
        @Header("Authorization") token: String
    ): FriendListResponse

    @POST("/api/friendship/add/{userId}")
    suspend fun addFriend(
        @Header("Authorization") token: String,
        @Path("userId") userId: String
    ): AddFriendResponse

    @DELETE("/api/friendship/remove/{userId}")
    suspend fun removeFriend(
        @Header("Authorization") token: String,
        @Path("userId") userId: String
    ): RemoveFriendResponse

    @POST("/api/friendship/accept/{userId}")
    suspend fun acceptFriend(
        @Header("Authorization") token: String,
        @Path("userId") userId: String
    ): AcceptFriendResponse

    @DELETE("/api/friendship/reject/{userId}")
    suspend fun rejectFriend(
        @Header("Authorization") token: String,
        @Path("userId") userId: String
    ): RejectFriendResponse

    @GET("/api/friendship/search")
    suspend fun searchUser(
        @Header("Authorization") token: String,
        @Query("username") username: String
    ): SearchUserResponse

}