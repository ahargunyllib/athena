package com.ahargunyllib.athena.features.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [UserEntity::class],
    version = 4
)
abstract class UserDatabase: RoomDatabase() {
    abstract fun getUserDAO(): UserDAO
}