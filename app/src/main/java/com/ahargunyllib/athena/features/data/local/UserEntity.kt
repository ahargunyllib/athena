package com.ahargunyllib.athena.features.data.local

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ahargunyllib.athena.features.utils.Constants
import java.time.LocalDateTime

@DatabaseView
@Entity(tableName = Constants.USER_DB_NAME)
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    val userId: String,

    @ColumnInfo(name = "full_name")
    val fullName: String,

    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "phone_number")
    val phoneNumber: String,

    @ColumnInfo(name = "date_of_birth")
    val dateOfBirth: String,

    @ColumnInfo(name = "token")
    val token: String,

    @ColumnInfo(name = "is_sharing_location")
    val isSharingLocation: Boolean,

    @ColumnInfo(name = "is_pause_all")
    val isPauseAll: Boolean,

    @ColumnInfo(name = "is_show_notification")
    val isShowNotification: Boolean,

    @ColumnInfo(name = "image_url")
    val imageUrl: String,

    @ColumnInfo(name = "created_at")
    val createdAt: String = LocalDateTime.now().toString(),

    @ColumnInfo(name = "updated_at")
    val updatedAt: String,

    )