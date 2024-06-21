package com.ahargunyllib.athena.features.domain.repository

import com.ahargunyllib.athena.features.data.remote.response.AcceptFriendResponse
import com.ahargunyllib.athena.features.data.remote.response.AddFriendResponse
import com.ahargunyllib.athena.features.data.remote.response.FriendListResponse
import com.ahargunyllib.athena.features.data.remote.response.RejectFriendResponse
import com.ahargunyllib.athena.features.data.remote.response.RemoveFriendResponse
import com.ahargunyllib.athena.features.data.remote.response.SearchUserResponse
import com.ahargunyllib.athena.features.utils.Response
import kotlinx.coroutines.flow.Flow

interface FriendshipRepository {
    suspend fun getFriendList(): Flow<Response<FriendListResponse>>
    suspend fun addFriendRequest(userId: String): Flow<Response<AddFriendResponse>>
    suspend fun removeFriend(userId: String): Flow<Response<RemoveFriendResponse>>
    suspend fun acceptFriend(userId: String): Flow<Response<AcceptFriendResponse>>
    suspend fun rejectFriend(userId: String): Flow<Response<RejectFriendResponse>>
    suspend fun searchUser(username: String): Flow<Response<SearchUserResponse>>
}