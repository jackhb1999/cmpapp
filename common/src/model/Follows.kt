package model

import kotlinx.serialization.Serializable

@Serializable
data class FollowsParams(
    val follower:String,
    val following:String,
    val isFollowing: Boolean
)