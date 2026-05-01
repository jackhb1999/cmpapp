package repository

import data.UserPreferences
import kotlinx.coroutines.withContext
import model.FollowUserData
import model.FollowsParams
import service.FollowsApiService
import util.DispatcherProvider

internal class FollowsRepositoryImpl(
    private val followsApiService: FollowsApiService,
    private val userPreferences: UserPreferences,
    private val dispatcher: DispatcherProvider
) : FollowsRepository {
    override suspend fun followUser(follower: String, following: String): Result<Boolean> {
        return withContext(dispatcher.io) {
                val userData = userPreferences.getUserData()
                val followsParams = FollowsParams(
                    userData.id,
                    following, isFollowing = true
                )
                val apiResponse = followsApiService.followUser(userData.token, followsParams)
              apiResponse.toResult()

        }
    }

    override suspend fun unfollowUser(follower: String, following: String): Result<Boolean> {
        return withContext(dispatcher.io) {
                val userData = userPreferences.getUserData()
                val followsParams = FollowsParams(
                    userData.id,
                    following, isFollowing = false
                )
                val apiResponse = followsApiService.followUser(userData.token, followsParams)
                apiResponse.toResult()
        }
    }

    override suspend fun getFollowers(
        userId: String,
        pageNumber: Int,
        pageSize: Int
    ): Result<List<FollowUserData>> {
        TODO("Not yet implemented")
    }

    override suspend fun getFollowing(
        userId: String,
        pageNumber: Int,
        pageSize: Int
    ): Result<List<FollowUserData>> {
        TODO("Not yet implemented")
    }

    override suspend fun getFollowingSuggestions(userId: String): Result<List<FollowUserData>> {
        return withContext(dispatcher.io) {
                val userData = userPreferences.getUserData()
                val apiResponse = followsApiService.getFollowableUser(userData.token, userData.id)
             apiResponse.toResult()
        }
    }
}