package com.hb.dao.user

import model.SignParams

interface UserDao {
    suspend fun inert(params: SignParams): UserRow?
    suspend fun findByEmail(email: String): UserRow?

    suspend fun findById(userId: String): UserRow?
}