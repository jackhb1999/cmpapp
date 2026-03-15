package com.hb.dao.follows

interface FollowsDao {

    suspend fun followUser(follower: ByteArray, following: ByteArray): Boolean

    suspend fun unfollowUser(follower: ByteArray, following: ByteArray): Boolean

    suspend fun getFollowers(userId: ByteArray, pageNumber: Int, pageSize: Int): List<ByteArray>

    suspend fun getFollowing(userId: ByteArray, pageNumber: Int, pageSize: Int): List<ByteArray>

    suspend fun isAlreadyFollowing(follower: ByteArray, following: ByteArray): Boolean


}