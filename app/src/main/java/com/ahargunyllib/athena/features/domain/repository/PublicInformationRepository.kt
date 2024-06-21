package com.ahargunyllib.athena.features.domain.repository

import android.content.Context
import com.ahargunyllib.athena.features.data.remote.response.PublicInformationResponse
import com.ahargunyllib.athena.features.data.remote.response.PublicInformationsResponse
import com.ahargunyllib.athena.features.data.remote.response.UpdateLocationResponse
import com.ahargunyllib.athena.features.domain.model.CreateCommentModel
import com.ahargunyllib.athena.features.domain.model.CreatePublicInformationModel
import com.ahargunyllib.athena.features.domain.model.CreateReportModel
import com.ahargunyllib.athena.features.utils.Response
import kotlinx.coroutines.flow.Flow

interface PublicInformationRepository{
    suspend fun getPublicInformation(): Flow<Response<PublicInformationsResponse>>
    suspend fun getPublicInformationById(publicInformationId: String): Flow<Response<PublicInformationResponse>>
    suspend fun createPublicInformation(context: Context, createPublicInformationModel: CreatePublicInformationModel): Flow<Response<UpdateLocationResponse>>
    suspend fun createComment(createCommentModel: CreateCommentModel, publicInformationId: String): Flow<Response<UpdateLocationResponse>>
    suspend fun reportPublicInformation(createReportModel: CreateReportModel, publicInformationId: String): Flow<Response<UpdateLocationResponse>>
}