package model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class NewCommentParams(
    val content: String,
    val postId: String,
    val userId: String
)


@Serializable
data class PostComment(
    val commentId: String,
    val userId: String,
    val content: String,
    val postId: String,
    val username: String,
    val userImageUrl: String?,
    val createdAt: LocalDateTime
)