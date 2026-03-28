package model

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class PostTextParams(
    val caption: String,
    val userId: String,
)

@Serializable
data class Post(
    val postId: String,
    val caption: String,
    val imageUrl: String,
//    val createdAt: String,
    val likesCount: Int,
    val commentsCount: Int,
    val userId: String,
    val username: String,
    val userImageUrl: String?,
    val isLiked: Boolean,
    val isOwnPost: Boolean,
)