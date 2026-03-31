package com.hb.dao.post

interface PostDao {

    suspend fun createPost(caption: String, imageUrl: String, userId: String): Boolean

    suspend fun getFeedsPost(userId: String, follows: List<String>, pageNumber: Int, pageSize: Int): List<PostRow>

    suspend fun getPostByUser(userId: String, pageNumber: Int, pageSize: Int): List<PostRow>

    suspend fun getPost(postId: String): PostRow?

    suspend fun updateLikesCount(postId:String,decrement: Boolean = false):Boolean

    suspend fun updateCommentsCount(postId: String, decrement: Boolean = false):Boolean

    suspend fun deletePost(postId: String): Boolean

}