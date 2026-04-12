package repository

import data.UserPreferences
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.io.IOException
import model.FollowsParams
import model.LikeParams
import model.Post
import model.PostTextParams
import service.PostApiService
import util.Constants
import util.DispatcherProvider
import util.Result


internal class PostRepositoryImpl(
    private val postApiService: PostApiService,
    private val userPreferences: UserPreferences,
    private val dispatcher: DispatcherProvider
) : PostRepository, PostLikesRepository {
    override suspend fun createPost(
        imageUrl: String,
        postTextParams: PostTextParams
    ): Result<Any> {
        TODO("Not yet implemented")
    }

    override suspend fun getFeedPosts(
        userId: String,
        pageNumber: Int,
        pageSize: Int
    ): Result<List<Post>> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = postApiService.getFeedPosts(
                    userToken = userData.token,
                    currentUserId = userData.id,
                    page = pageNumber,
                    pageSize = pageSize
                )
                when (apiResponse.code) {
                    HttpStatusCode.OK.value -> {
                        Result.Success(data = apiResponse.data)
                    }

                    HttpStatusCode.BadRequest.value -> {
                        Result.Error(message = apiResponse.message)
                    }

                    HttpStatusCode.Forbidden.value -> {
                        Result.Success(data = emptyList())
                    }

                    else -> {
                        Result.Error()
                    }
                }
            } catch (ioException: IOException) {
                Result.Error(message = Constants.NO_INTERNET_CONNECTION)
            } catch (t: Throwable) {
                Result.Error(message = t.message)
            }
        }
    }

    override suspend fun getPostsByUser(
        postsOwnerId: String,
        currentUserId: String,
        pageNumber: Int,
        pageSize: Int
    ): Result<List<Post>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPost(postId: String, currentUserId: String): Result<Post> {
        TODO("Not yet implemented")
    }

    override suspend fun deletePost(postId: String): Result<Any> {
        TODO("Not yet implemented")
    }

    override suspend fun addLike(params: LikeParams): Result<Any> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val likeParams = LikeParams(
                    userId = userData.id,
                    postId = params.postId,
                )
                val apiResponse = postApiService.likePost(userToken = userData.token, likeParams)
                if (apiResponse.code == HttpStatusCode.OK.value) {
                    Result.Success(data = apiResponse.data)
                } else {
                    Result.Error(message = apiResponse.message)
                }
            } catch (ioException: IOException) {
                Result.Error(message = Constants.NO_INTERNET_CONNECTION)
            } catch (t: Throwable) {
                Result.Error(message = t.message)
            }
        }
    }

    override suspend fun removeLike(params: LikeParams): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val likeParams = LikeParams(
                    userId = userData.id,
                    postId = params.postId,
                )
                val apiResponse = postApiService.unlikePost(userToken = userData.token, likeParams)
                if (apiResponse.code == HttpStatusCode.OK.value) {
                    Result.Success(data = apiResponse.data)
                } else {
                    Result.Error(message = apiResponse.message)
                }
            } catch (ioException: IOException) {
                Result.Error(message = Constants.NO_INTERNET_CONNECTION)
            } catch (t: Throwable) {
                Result.Error(message = t.message)
            }
        }
    }
}