package repository

import model.NewCommentParams
import model.PostComment

interface PostCommentsRepository {

    suspend fun addComment(params: NewCommentParams): Result<PostComment>

    suspend fun removeComment(commentId: String, postId: String,userId: String): Result<Boolean>

    suspend fun getPostComments(postId: String, pageNumber: Int, pageSize: Int): Result<List<PostComment>>

}