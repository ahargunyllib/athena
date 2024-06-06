package com.ahargunyllib.athena.features.data.repository

import android.util.Log
import com.ahargunyllib.athena.features.data.local.UserDAO
import com.ahargunyllib.athena.features.data.local.UserDatabase
import com.ahargunyllib.athena.features.data.local.UserEntity
import com.ahargunyllib.athena.features.data.remote.API
import com.ahargunyllib.athena.features.data.remote.response.UserResponse
import com.ahargunyllib.athena.features.data.remote.response.UsersResponse
import com.ahargunyllib.athena.features.domain.repository.UserRepository
import com.ahargunyllib.athena.features.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.HttpURLConnection.HTTP_OK
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val db: UserDatabase
) : UserRepository {
    override suspend fun getToken(): String? {
        try {
            val userDAO = db.getUserDAO()
            val user = userDAO.getUser()
            return user.token
        } catch (e: Exception) {
            Log.e("UserRepositoryImpl", "getToken: ${e.message}")
            return null
        }
    }

    override suspend fun insertUser(user: UserEntity) {
        try {
            val userDAO = db.getUserDAO()
            userDAO.upsert(user)
        } catch (e: Exception) {
            Log.e("UserRepositoryImpl", "insertUser: ${e.message}")
        }
    }

    override suspend fun getUser(): UserEntity? {
        try {
            val userDAO = db.getUserDAO()
            return userDAO.getUser()
        } catch (e: Exception) {
            Log.e("UserRepositoryImpl", "getUser: ${e.message}")
            return null
        }
    }

    override suspend fun deleteUser() {
        try {
            val userDAO = db.getUserDAO()
            userDAO.deleteUser()
        } catch (e: Exception) {
            Log.e("UserRepositoryImpl", "deleteUser: ${e.message}")
        }
    }
}