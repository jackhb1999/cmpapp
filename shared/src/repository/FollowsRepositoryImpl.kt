package repository

import data.UserPreferences
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.withContext
import kotlinx.io.IOException
import model.FollowUserData
import model.FollowsParams
import service.FollowsApiService
import util.Constants
import util.DispatcherProvider
import util.Result

internal class FollowsRepositoryImpl(
    private val followsApiService: FollowsApiService,
    private val userPreferences: UserPreferences,
    private val dispatcher: DispatcherProvider
) : FollowsRepository {
    override suspend fun followUser(follower: String, following: String): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val followsParams = FollowsParams(
                    userData.id,
                    following, isFollowing = true
                )
                val apiResponse = followsApiService.followUser(userData.token, followsParams)
                if (apiResponse.code == HttpStatusCode.OK) {
                    Result.Success(data = apiResponse.data)
                } else {
                    Result.Error(message = apiResponse.code.description)
                }
            } catch (ioException: IOException) {
                Result.Error(message = Constants.NO_INTERNET_CONNECTION)
            } catch (t: Throwable) {
                Result.Error(message = t.message)
            }
        }
    }

    override suspend fun unfollowUser(follower: String, following: String): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val followsParams = FollowsParams(
                    userData.id,
                    following, isFollowing = false
                )
                val apiResponse = followsApiService.followUser(userData.token, followsParams)
                if (apiResponse.code == HttpStatusCode.OK) {
                    Result.Success(data = apiResponse.data)
                } else {
                    Result.Error(message = apiResponse.code.description)
                }
            } catch (ioException: IOException) {
                Result.Error(message = Constants.NO_INTERNET_CONNECTION)
            } catch (t: Throwable) {
                Result.Error(message = t.message)
            }
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
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = followsApiService.getFollowableUser(userData.token, userData.id)
                when (apiResponse.code) {
                    HttpStatusCode.OK -> {
                        Result.Success(data = apiResponse.data)
                    }

                    HttpStatusCode.BadRequest -> {
                        Result.Error(message = apiResponse.code.description)
                    }

                    HttpStatusCode.Forbidden -> {
                        Result.Success(data = emptyList())
                    }

                    else -> {
                        Result.Error()
                    }
                }
            } catch (_: IOException) {
                Result.Error(message = Constants.NO_INTERNET_CONNECTION)
            } catch (t: Throwable) {
                Result.Error(message = t.message)
            }
        }
    }
}