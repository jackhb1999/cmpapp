package com.hb.repository

import com.hb.dao.post.PostDao
import com.hb.dao.post_comments.PostCommentRow
import com.hb.dao.post_comments.PostCommentsDao
import com.hb.dao.post_comments.PostCommentsTable
import model.NewCommentParams
import model.PostComment
import repository.PostCommentsRepository
import util.Result

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
            Result.Success(postComment)
        } else {
            Result.Error(message = "Something went wrong")
        }
    }

    override suspend fun removeComment(commentId: String, postId: String, userId: String): Result<Any> {
        val commentRow = postCommentsDao.findComment(commentId, postId)
        return if (commentRow != null) {
            val postRow = postDao.getPost(postId)
            if ((userId != commentRow.userId) && (userId != postRow?.userId)) {
                Result.Error<Any>(message = "没权限进行删除！")
            }
            val removeComment = postCommentsDao.removeComment(commentId, postId)
            if (removeComment) {
                Result.Success(Unit)
            } else {
                Result.Error(message = "删除失败！")
            }
        } else {
            Result.Error(message = "评论不存在！")
        }
    }

    override suspend fun getPostComments(
        postId: String,
        pageNumber: Int,
        pageSize: Int
    ): Result<List<PostComment>> {
        val list = postCommentsDao.getComments(postId, pageNumber, pageSize)?.map { toPostComment(it) }
        return if (!list.isNullOrEmpty()) {
            Result.Success(list)
        } else {
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