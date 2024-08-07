package com.ahargunyllib.athena.di

import com.ahargunyllib.athena.features.data.repository.AuthRepositoryImpl
import com.ahargunyllib.athena.features.data.repository.ChatRepositoryImpl
import com.ahargunyllib.athena.features.data.repository.FriendshipRepositoryImpl
import com.ahargunyllib.athena.features.data.repository.LocationRepositoryImpl
import com.ahargunyllib.athena.features.data.repository.PublicInformationRepositoryImpl
import com.ahargunyllib.athena.features.data.repository.UserRepositoryImpl
import com.ahargunyllib.athena.features.domain.repository.AuthRepository
import com.ahargunyllib.athena.features.domain.repository.ChatRepository
import com.ahargunyllib.athena.features.domain.repository.FriendshipRepository
import com.ahargunyllib.athena.features.domain.repository.LocationRepository
import com.ahargunyllib.athena.features.domain.repository.PublicInformationRepository
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

    @Binds
    @Singleton
    abstract fun bindLocationRepository(locationRepositoryImpl: LocationRepositoryImpl): LocationRepository

    @Binds
    @Singleton
    abstract fun bindChatRepository(chatRepositoryImpl: ChatRepositoryImpl): ChatRepository

    @Binds
    @Singleton
    abstract fun bindPublicInformationRepository(publicInformationRepositoryImpl: PublicInformationRepositoryImpl): PublicInformationRepository
}