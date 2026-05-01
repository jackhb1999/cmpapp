package com.hb.repository

import com.hb.dao.user.UserDao
import model.AuthResponse
import model.UserSettingsData
import model.SignInParams
import model.SignUpParams
import com.hb.plugins.generateJWTToken
import com.hb.security.hashPassword
import io.ktor.http.*
import repository.UserRepository

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun signUp(params: SignUpParams): Result<AuthResponse> {
        return if (userAlreadyExist(params.email)) {
            throw Throwable("用户已存在")
        } else {
            val insertedUser = userDao.inert(params)
            if (insertedUser == null) {
             throw Throwable("新增用户失败")
            } else {
                Result.success(
                     AuthResponse(
                        data = UserSettingsData(
                            id = insertedUser.id,
                            name = insertedUser.name,
                            bio = insertedUser.bio,
                            avatar = insertedUser.imageUrl,
                            token = generateJWTToken(insertedUser.email),
                        )
                    )
                )
            }
        }
    }

    override suspend fun signIn(params: SignInParams): Result<AuthResponse> {
        val user = userDao.findByEmail(params.email)
        return if (user == null) {
          throw Throwable("没找到用户")
        } else {
            if (user.password == hashPassword(params.password)) {
                Result.success(
                    AuthResponse(
                        data = UserSettingsData(
                            id = user.id,
                            name = user.name,
                            bio = user.bio,
                            avatar = user.imageUrl,
                            token = generateJWTToken(user.email),
                            followersCount = user.followersCount,
                            followingCount = user.followingCount,
                        )
                    )
                )
            } else {
                throw Throwable("密码格式有问题")
            }
        }
    }


    private suspend fun userAlreadyExist(email: String): Boolean {
        return userDao.findByEmail(email) != null
    }
}