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

    @Query("UPDATE users SET is_sharing_location = :isSharingLocation WHERE user_id = :userId")
    suspend fun updateIsSharingLocation(userId: String, isSharingLocation: Boolean)

    @Query("UPDATE users SET is_pause_all = :isPauseAll WHERE user_id = :userId")
    suspend fun updateIsPauseAll(userId: String, isPauseAll: Boolean)

    @Query("UPDATE users SET is_show_notification = :isShowNotification WHERE user_id = :userId")
    suspend fun updateIsShowNotification(userId: String, isShowNotification: Boolean)

}