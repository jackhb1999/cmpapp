package com.hb.repository

import com.hb.dao.post.PostDao
import com.hb.dao.post_comments.PostCommentRow
import com.hb.dao.post_comments.PostCommentsDao
import model.NewCommentParams
import model.PostComment
import repository.PostCommentsRepository


class PostCommentsRepositoryImpl(
    private val postCommentsDao: PostCommentsDao,
    private val postDao: PostDao
) : PostCommentsRepository {
    override suspend fun addComment(params: NewCommentParams): Result<PostComment> {
        val postComment = postCommentsDao.addComment(
            postId = params.postId,
            content = params.content,
            userId = params.userId,
        )?.let { toPostComment(it) }
        return if (postComment != null) {
            postDao.updateCommentsCount(params.postId, true)
            Result.success(postComment)
        } else {
            Result.failure(Throwable("Failed to add comment"))
        }
    }

    override suspend fun removeComment(commentId: String, postId: String, userId: String): Result<Boolean> {
        val commentRow = postCommentsDao.findComment(commentId, postId)
        return if (commentRow != null) {
            val postRow = postDao.getPost(postId)
            if ((userId != commentRow.userId) && (userId != postRow?.userId)) {
                Result.failure<Unit>(Throwable(message = "没权限进行删除！"))
            }
            val removeComment = postCommentsDao.removeComment(commentId, postId)
            if (removeComment) {
                Result.success(true)
            } else {
                Result.failure(Throwable(message = "删除失败！"))
            }
        } else {
            Result.failure(Throwable(message = "评论不存在！"))
        }
    }

    override suspend fun getPostComments(
        postId: String,
        pageNumber: Int,
        pageSize: Int
    ): Result<List<PostComment>> {
        val list = postCommentsDao.getComments(postId, pageNumber, pageSize)?.map { toPostComment(it) }
        return if (!list.isNullOrEmpty()) {
            Result.success(list)
        } else {
            Result.failure(Throwable(message = "No comments were found"))
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