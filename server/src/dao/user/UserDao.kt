package com.hb.dao.user

import model.SignUpParams

interface UserDao {
    suspend fun inert(params: SignUpParams): UserRow?
    suspend fun findByEmail(email: String): UserRow?

    suspend fun updateFollowsCount(follower: String,following: String,isFollowing: Boolean): Boolean
}