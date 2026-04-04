package model

import kotlinx.serialization.Serializable

@Serializable
data class LikeParams(
    val postId: String,
    val userId: String,
)