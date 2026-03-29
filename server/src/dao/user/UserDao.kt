package com.hb.dao.user

import model.SignUpParams

interface UserDao {
    suspend fun inert(params: SignUpParams): UserRow?
    suspend fun findByEmail(email: String): UserRow?

    suspend fun findById(userId: String): UserRow?

    suspend fun updateUser(userId: String, name: String, bio: String, imageUrl: String?): Boolean

    suspend fun updateFollowsCount(follower: String, following: String, isFollowing: Boolean): Boolean
}