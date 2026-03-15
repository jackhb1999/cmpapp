package com.hb.dao.user

import com.hb.model.UserRow
import model.SignUpParams

interface UserDao {
    suspend fun inert(params: SignUpParams): UserRow?
    suspend fun findByEmail(email: String): UserRow?

    suspend fun updateFollowsCount(follower: ByteArray,following: ByteArray,isFollowing: Boolean): Boolean
}