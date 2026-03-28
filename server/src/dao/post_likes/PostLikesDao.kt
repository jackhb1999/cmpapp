package com.hb.dao.post_likes

import model.PostTextParams

interface PostLikesDao {
    suspend fun addLike(postId: String, userId: String): Boolean

    suspend fun removeLike(postId: String, userId: String): Boolean

    suspend fun isPostLiked(postId: String, userId: String): Boolean
}