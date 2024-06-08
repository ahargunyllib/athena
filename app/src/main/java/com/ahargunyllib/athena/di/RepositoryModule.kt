package com.ahargunyllib.athena.di

import com.ahargunyllib.athena.features.data.repository.AuthRepositoryImpl
import com.ahargunyllib.athena.features.data.repository.FriendshipRepositoryImpl
import com.ahargunyllib.athena.features.data.repository.UserRepositoryImpl
import com.ahargunyllib.athena.features.domain.repository.AuthRepository
import com.ahargunyllib.athena.features.domain.repository.FriendshipRepository
import com.ahargunyllib.athena.features.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindFriendshipRepository(friendshipRepositoryImpl: FriendshipRepositoryImpl): FriendshipRepository
}