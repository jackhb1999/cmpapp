package model

import kotlinx.serialization.Serializable

@Serializable
data class FollowsParams(
    val follower: String,
    val following: String,
    val isFollowing: Boolean
)


@Serializable
data class FollowUserData(
    val id: String,
    val name: String,
    val bio: String,
    val imageUrl: String? = null,
    val isFollowing: Boolean
)