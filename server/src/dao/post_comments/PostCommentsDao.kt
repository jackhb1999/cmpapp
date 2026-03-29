package com.hb.dao.post_comments

interface PostCommentsDao {

    suspend fun addComment(postId: String, userId: String, content: String): PostCommentRow?

    suspend fun removeComment(commentId: String, postId: String): Boolean

    suspend fun findComment(commentId: String, postId: String): PostCommentRow?

    suspend fun getComments(postId: String, pageNumber: Int, pageSize: Int): List<PostCommentRow>?
}