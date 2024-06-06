package com.ahargunyllib.athena.features.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface UserDAO {
    @Upsert
    suspend fun upsert(userEntity: UserEntity)

    @Query("SELECT * FROM users")
    suspend fun getUser(): UserEntity

    @Query("DELETE FROM users")
    suspend fun deleteUser()
}