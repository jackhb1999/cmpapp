package repository

import data.UserPreferences
import kotlinx.coroutines.withContext
import model.LikeParams
import model.Post
import model.PostTextParams
import service.PostApiService
import util.DispatcherProvider


internal class PostRepositoryImpl(
    private val postApiService: PostApiService,
    private val userPreferences: UserPreferences,
    private val dispatcher: DispatcherProvider
) : PostRepository, PostLikesRepository {
    override suspend fun createPost(
        imageUrl: String,
        postTextParams: PostTextParams
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getFeedPosts(
        userId: String,
        pageNumber: Int,
        pageSize: Int
    ): Result<List<Post>> {
        return withContext(dispatcher.io) {
            val userData = userPreferences.getUserData()
            val apiResponse = postApiService.getFeedPosts(
                userToken = userData.token,
                currentUserId = userData.id,
                page = pageNumber,
                pageSize = pageSize
            )
            apiResponse.toResult()
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

    override suspend fun deletePost(postId: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun addLike(params: LikeParams): Result<Boolean> {
        return withContext(dispatcher.io) {
            val userData = userPreferences.getUserData()
            val likeParams = LikeParams(
                userId = userData.id,
                postId = params.postId,
            )
            val apiResponse = postApiService.likePost(userToken = userData.token, likeParams)
            apiResponse.toResult()
        }
    }

    override suspend fun removeLike(params: LikeParams): Result<Boolean> {
        return withContext(dispatcher.io) {
            val userData = userPreferences.getUserData()
            val likeParams = LikeParams(
                userId = userData.id,
                postId = params.postId,
            )
            val apiResponse = postApiService.unlikePost(userToken = userData.token, likeParams)
            apiResponse.toResult()
        }
    }
}