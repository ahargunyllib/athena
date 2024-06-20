package com.ahargunyllib.athena.features.data.remote.response

data class PublicInformationsResponse(
    val statusCode: Int,
    val message: String,
    val data: List<MinPublicInformation>,
)

data class MinPublicInformation(
    val publicInformationId: String,
    val authorId: String,
    val author: Author,
    val latitude: Float,
    val longitude: Float,
    val postId: String,
    val post: Post
)

data class Author(
    val userId: String,
    val username: String,
    val fullName: String,
    val imageUrl: String,
)

data class Post(
    val postId: String,
    val content: String,
    val createdAt: String,
    val imageUrl: String
)

data class PublicInformationResponse(
    val statusCode: Int,
    val message: String,
    val data: MaxPublicInformation
)

data class MaxPublicInformation(
    val publicInformationId: String,
    val authorId: String,
    val author: Author,
    val postId: String,
    val post: PostWithComments,
)

data class PostWithComments(
    val postId: String,
    val content: String,
    val createdAt: String,
    val imageUrl: String,
    val comments: List<Comment>
)

data class Comment(
    val commentId: String,
    val authorId: String,
    val author: Author,
    val content: String,
    val createdAt: String,
)