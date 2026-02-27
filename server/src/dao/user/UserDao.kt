package com.hb.dao.user

import model.SignUpParams
import com.hb.model.User

interface UserDao {
    suspend fun inert(params: SignUpParams): User?
    suspend fun findByEmail(email: String): User?
}