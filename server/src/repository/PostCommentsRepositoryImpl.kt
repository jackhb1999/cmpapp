package com.hb.repository

import com.hb.dao.post_comments.PostCommentRow
import com.hb.dao.post_comments.PostCommentsDao
import model.NewCommentParams
import model.PostComment
import repository.PostCommentsRepository
import util.Result

class PostCommentsRepositoryImpl(
    private val postCommentsDao: PostCommentsDao,
) : PostCommentsRepository {
    override suspend fun addComment(params: NewCommentParams): Result<PostComment> {
        val postComment = postCommentsDao.addComment(
            postId = params.postId,
            content = params.content,
            userId = params.userId,
        )?.let { toPostComment(it) }
        return if (postComment != null) {
            Result.Success(postComment)
        } else {
            Result.Error(message = "Something went wrong")
        }
    }

    override suspend fun removeComment(commentId: String, postId: String): Result<Any> {
        TODO("Not yet implemented")
    }

    override suspend fun getPostComments(
        postId: String,
        pageNumber: Int,
        pageSize: Int
    ): Result<List<PostComment>> {
       val list = postCommentsDao.getComments(postId, pageNumber, pageSize)?.map { toPostComment(it) }
        return if (!list.isNullOrEmpty()) {
            Result.Success(list)
        }else{
            Result.Error(message = "No comments were found")
        }
    }

    private fun toPostComment(postCommentRow: PostCommentRow): PostComment {
        return PostComment(
            commentId = postCommentRow.commentId,
            userId = postCommentRow.userId,
            content = postCommentRow.content,
            postId = postCommentRow.postId,
            username = postCommentRow.username,
            userImageUrl = postCommentRow.userImageUrl,
            createdAt = postCommentRow.createdAt,
        )
    }
}