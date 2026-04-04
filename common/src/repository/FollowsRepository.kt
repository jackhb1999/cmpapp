package repository

import model.FollowUserData
import util.Result

interface FollowsRepository {
    suspend fun followUser(follower: String, following: String): Result<Any>
    suspend fun unfollowUser(follower: String, following: String): Result<Any>

    suspend fun getFollowers(userId: String, pageNumber: Int, pageSize: Int): Result<List<FollowUserData>>
    suspend fun getFollowing(userId: String, pageNumber: Int, pageSize: Int): Result<List<FollowUserData>>

    suspend fun getFollowingSuggestions(userId: String): Result<List<FollowUserData>>

}