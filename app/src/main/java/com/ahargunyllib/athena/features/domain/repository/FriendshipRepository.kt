package com.ahargunyllib.athena.features.domain.repository

import android.content.Context
import com.ahargunyllib.athena.features.data.remote.response.AcceptFriendResponse
import com.ahargunyllib.athena.features.data.remote.response.AddFriendResponse
import com.ahargunyllib.athena.features.data.remote.response.FriendListResponse
import com.ahargunyllib.athena.features.data.remote.response.RejectFriendResponse
import com.ahargunyllib.athena.features.data.remote.response.RemoveFriendResponse
import com.ahargunyllib.athena.features.data.remote.response.SearchUserResponse
import com.ahargunyllib.athena.features.utils.Response
import kotlinx.coroutines.flow.Flow

interface FriendshipRepository {
    suspend fun getFriendList(context: Context): Flow<Response<FriendListResponse>>
    suspend fun addFriendRequest(context: Context, userId: String): Flow<Response<AddFriendResponse>>
    suspend fun removeFriend(context: Context, userId: String): Flow<Response<RemoveFriendResponse>>
    suspend fun acceptFriend(context: Context, userId: String): Flow<Response<AcceptFriendResponse>>
    suspend fun rejectFriend(context: Context, userId: String): Flow<Response<RejectFriendResponse>>
    suspend fun searchUser(context: Context, username: String): Flow<Response<SearchUserResponse>>
}