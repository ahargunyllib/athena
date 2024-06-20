package com.ahargunyllib.athena.features.domain.repository

import android.content.Context
import com.ahargunyllib.athena.features.data.local.UserEntity
import com.ahargunyllib.athena.features.data.remote.response.ProfileUserResponse
import com.ahargunyllib.athena.features.data.remote.response.RegisterResponse
import com.ahargunyllib.athena.features.data.remote.response.UserResponse
import com.ahargunyllib.athena.features.data.remote.response.UsersResponse
import com.ahargunyllib.athena.features.domain.model.UpdateModel
import com.ahargunyllib.athena.features.utils.Response
import kotlinx.coroutines.flow.Flow


interface UserRepository {
    suspend fun getToken(): String?
    suspend fun insertUser(user: UserEntity)
    suspend fun getUser(): UserEntity?
    suspend fun deleteUser()
    suspend fun updateIsSharingLocation(userId: String, isSharingLocation: Boolean)
    suspend fun updateIsPauseAll(userId: String, isPauseAll: Boolean)
    suspend fun updateIsShowNotification(userId: String, isShowNotification: Boolean)
    suspend fun getUser(context: Context, userId: String): Flow<Response<ProfileUserResponse>>
    suspend fun updateUser(context: Context, updateModel: UpdateModel): Flow<Response<RegisterResponse>>
}