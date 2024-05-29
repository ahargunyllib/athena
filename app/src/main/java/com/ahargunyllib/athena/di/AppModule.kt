package com.ahargunyllib.athena.di

import android.content.Context
import androidx.room.Room
import com.ahargunyllib.athena.features.data.local.UserDatabase
import com.ahargunyllib.athena.features.data.remote.API
import com.ahargunyllib.athena.features.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideAPI(): API {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(API::class.java)
    }

    @Singleton
    @Provides
    fun provideUserDB(@ApplicationContext context: Context): UserDatabase {
        return Room.databaseBuilder(
            context,
            UserDatabase::class.java,
            Constants.USER_DB_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideUserDAO(userDatabase: UserDatabase) = userDatabase.getUserDAO()
}