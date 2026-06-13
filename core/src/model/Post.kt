package model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class PostParams(
    val content: String,
    val url: String,
    val plateId: String,
    val userId: String,
)

@Serializable
data class Post(
    val postId: String,
    val content: String,
    val url: String?,
    val plateId: String,
    val plateName: String,
    val likesCount: Int,
    val notLikesCount: Int,
    val commentsCount: Int,
    val userId: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)