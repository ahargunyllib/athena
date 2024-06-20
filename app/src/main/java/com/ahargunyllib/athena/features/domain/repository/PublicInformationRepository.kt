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
    suspend fun getPublicInformation(context: Context): Flow<Response<PublicInformationsResponse>>
    suspend fun getPublicInformationById(context: Context, publicInformationId: String): Flow<Response<PublicInformationResponse>>
    suspend fun createPublicInformation(context: Context, createPublicInformationModel: CreatePublicInformationModel): Flow<Response<UpdateLocationResponse>>
    suspend fun createComment(context: Context, createCommentModel: CreateCommentModel, publicInformationId: String): Flow<Response<UpdateLocationResponse>>
    suspend fun reportPublicInformation(context: Context, createReportModel: CreateReportModel, publicInformationId: String): Flow<Response<UpdateLocationResponse>>
}