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
    val userId: String,

    @ColumnInfo(name = "full_name")
    val fullName: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "created_at")
    val createdAt: String = LocalDateTime.now().toString(),

    @ColumnInfo(name = "updated_at")
    val updatedAt: String,

    )