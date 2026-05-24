package model

import io.ktor.http.HttpStatusCode
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


@Serializable
data class FollowsApiResponseData(
    val success: Boolean,
    val follows: List<FollowUserData> = listOf(),
    val message: String? = null
)


data class FollowsApiResponse(
    val code: HttpStatusCode,
    val data: List<FollowUserData>
)

@Serializable
data class FollowOrUnfollowResponseData(
    val success: Boolean,
    val message: String? = null
)


data class FollowOrUnfollowApiResponse(
    val code: HttpStatusCode,
    val data: Boolean
)