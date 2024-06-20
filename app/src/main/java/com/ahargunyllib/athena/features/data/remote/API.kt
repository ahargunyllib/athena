package com.ahargunyllib.athena.features.data.remote

import com.ahargunyllib.athena.features.data.remote.response.AcceptFriendResponse
import com.ahargunyllib.athena.features.data.remote.response.AddFriendResponse
import com.ahargunyllib.athena.features.data.remote.response.ChatRoomResponse
import com.ahargunyllib.athena.features.data.remote.response.FriendListResponse
import com.ahargunyllib.athena.features.data.remote.response.FriendsLocationResponse
import com.ahargunyllib.athena.features.data.remote.response.LoginResponse
import com.ahargunyllib.athena.features.data.remote.response.MessagesResponse
import com.ahargunyllib.athena.features.data.remote.response.MinUserResponse
import com.ahargunyllib.athena.features.data.remote.response.ProfileUserResponse
import com.ahargunyllib.athena.features.data.remote.response.PublicInformationResponse
import com.ahargunyllib.athena.features.data.remote.response.PublicInformationsResponse
import com.ahargunyllib.athena.features.data.remote.response.RefreshTokenResponse
import com.ahargunyllib.athena.features.data.remote.response.RegisterResponse
import com.ahargunyllib.athena.features.data.remote.response.RejectFriendResponse
import com.ahargunyllib.athena.features.data.remote.response.RemoveFriendResponse
import com.ahargunyllib.athena.features.data.remote.response.SearchUserResponse
import com.ahargunyllib.athena.features.data.remote.response.UpdateLocationResponse
import com.ahargunyllib.athena.features.data.remote.response.UserRegisterResponse
import com.ahargunyllib.athena.features.data.remote.response.UsersResponse
import com.ahargunyllib.athena.features.domain.model.CreateCommentModel
import com.ahargunyllib.athena.features.domain.model.CreateReportModel
import com.ahargunyllib.athena.features.domain.model.CredentialsModel
import com.ahargunyllib.athena.features.domain.model.LocationModel
import com.ahargunyllib.athena.features.domain.model.LoginModel
import com.ahargunyllib.athena.features.domain.model.RegisterModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
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

    @GET("/api/location/friends")
    suspend fun getFriendsLocation(
        @Header("Authorization") token: String
    ): FriendsLocationResponse

    @PATCH("/api/location/update")
    suspend fun updateLocation(
        @Header("Authorization") token: String,
        @Body locationModel: LocationModel
    ): UpdateLocationResponse

    @GET("/api/chat/room")
    suspend fun getChatRooms(
        @Header("Authorization") token: String
    ): ChatRoomResponse

    @GET("/api/chat/room/{chatRoomId}")
    suspend fun getMessages(
        @Header("Authorization") token: String,
        @Path("chatRoomId") chatRoomId: String
    ): MessagesResponse

    @GET("/api/user/{userId}")
    suspend fun getUser(
        @Header("Authorization") token: String,
        @Path("userId") userId: String
    ): ProfileUserResponse

    @Multipart
    @PUT("/api/user/update")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Part("fullName") fullName: RequestBody,
        @Part("username") username: RequestBody,
        @Part("phoneNumber") phoneNumber: RequestBody,
        @Part("dateOfBirth") dateOfBirth: RequestBody,
        @Part avatar : MultipartBody.Part?,
    ): RegisterResponse

    @PUT("/api/user/update-credentials")
    suspend fun updateCredentials(
        @Header("Authorization") token: String,
        @Body credentialsModel: CredentialsModel
    ): RegisterResponse

    @POST("/api/sos")
    suspend fun sos(
        @Header("Authorization") token: String
    ): UpdateLocationResponse

    @GET("/api/public-info")
    suspend fun getPublicInformation(
        @Header("Authorization") token: String
    ): PublicInformationsResponse

    @GET("/api/public-info/{publicInformationId}")
    suspend fun getPublicInformationById(
        @Header("Authorization") token: String,
        @Path("publicInformationId") publicInformationId: String
    ): PublicInformationResponse

    @Multipart
    @POST("/api/public-info")
    suspend fun createPublicInformation(
        @Header("Authorization") token: String,
        @Part("latitude") latitude: RequestBody,
        @Part("longitude") longitude: RequestBody,
        @Part("content") content: RequestBody,
        @Part avatar : MultipartBody.Part?,
    ): UpdateLocationResponse

    @POST("/api/public-info/{publicInformationId}/comment")
    suspend fun createComment(
        @Header("Authorization") token: String,
        @Path("publicInformationId") publicInformationId: String,
        @Body createCommentModel: CreateCommentModel
    ): UpdateLocationResponse

    @POST("/api/public-info/{publicInformationId}/report")
    suspend fun reportPublicInformation(
        @Header("Authorization") token: String,
        @Path("publicInformationId") publicInformationId: String,
        @Body createReportModel: CreateReportModel
    ): UpdateLocationResponse
}