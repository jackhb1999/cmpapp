package repository

import data.UserPreferences
import kotlinx.coroutines.withContext
import model.NewCommentParams
import model.PostComment
import service.PostApiService
import service.PostCommentsApiService
import util.DispatcherProvider

internal class PostCommentsRepositoryImpl(
    private val postCommentsApiService: PostCommentsApiService,
    private val userPreferences: UserPreferences,
    private val dispatcher: DispatcherProvider
) : PostCommentsRepository {
    override suspend fun addComment(params: NewCommentParams): Result<PostComment> {
        return withContext(dispatcher.io) {
            val currentUserData = userPreferences.getUserData()
            val params = params.copy(
                userId = currentUserData.id
            )
            val apiResponse = postCommentsApiService.addComment(
                comment = params,
                userToken = currentUserData.token
            )
            apiResponse.toResult()
        }
    }

    override suspend fun removeComment(
        commentId: String,
        postId: String,
        userId: String
    ): Result<Boolean> {
        return withContext(dispatcher.io) {
            val currentUserData = userPreferences.getUserData()
            val apiResponse = postCommentsApiService.removePostComments(
                commentId = commentId,
                postId = postId,
                userId = currentUserData.id,
                userToken = currentUserData.token
            )
            apiResponse.toResult()
        }
    }

    override suspend fun getPostComments(
        postId: String,
        pageNumber: Int,
        pageSize: Int
    ): Result<List<PostComment>> {
        return withContext(dispatcher.io) {
            val currentUserData = userPreferences.getUserData()
            val apiResponse = postCommentsApiService.getPostComments(
                userToken = currentUserData.token,
                postId = postId,
                page = pageNumber,
                pageSize = pageSize,
            )
            apiResponse.toResult()
        }
    }
}