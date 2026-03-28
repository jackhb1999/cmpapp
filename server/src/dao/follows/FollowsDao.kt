package com.hb.dao.follows

interface FollowsDao {

    suspend fun followUser(follower: String, following: String): Boolean

    suspend fun unfollowUser(follower: String, following: String): Boolean

    suspend fun getFollowers(userId: String, pageNumber: Int, pageSize: Int): List<String>

    suspend fun getFollowing(userId: String, pageNumber: Int? = null, pageSize: Int? = null): List<String>

    suspend fun isAlreadyFollowing(follower: String, following: String): Boolean


}