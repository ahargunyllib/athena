package com.ahargunyllib.athena.features.domain.repository

import com.ahargunyllib.athena.features.data.remote.response.FriendsLocationResponse
import com.ahargunyllib.athena.features.data.remote.response.UpdateLocationResponse
import com.ahargunyllib.athena.features.domain.model.LocationModel
import com.ahargunyllib.athena.features.utils.Response
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    suspend fun getFriendsLocation(): Flow<Response<FriendsLocationResponse>>
    suspend fun updateLocation(request: LocationModel): Flow<Response<UpdateLocationResponse>>
    suspend fun sos(): Flow<Response<UpdateLocationResponse>>
}