package model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Serializable
data class UpdateUserParams(
    val userId: String,
    val name: String,
    val bio: String,
    var imageUrl: String? = null
)

@Serializable
data class Profile(
    val id: String,
    val name: String,
    val bio: String,
    val imageUrl: String? = null,
    val followersCount: Int = 0,
    val followingCount: Int = 0,
    val isFollowing: Boolean,
    val isOwnProfile: Boolean,
)