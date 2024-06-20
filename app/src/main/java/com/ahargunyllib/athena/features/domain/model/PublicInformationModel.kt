package com.ahargunyllib.athena.features.domain.model

import android.net.Uri

data class CreatePublicInformationModel(
    val latitude: Float,
    val longitude: Float,
    val content: String,
    val imageUri: Uri
)

data class CreateCommentModel(
    val content: String
)

data class CreateReportModel(
    val reason: String
)