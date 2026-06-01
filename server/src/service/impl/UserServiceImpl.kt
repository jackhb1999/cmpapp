package com.hb.service.impl

import com.hb.dao.user.UserDao
import com.hb.security.hashPassword
import model.SignParams
import model.User
import service.UserService
import util.ActionResult
import util.send

class UserServiceImpl(
    private val userDao: UserDao
) : UserService {
    override suspend fun signUp(params: SignParams): ActionResult<User> {
        val result = if (userAlreadyExist(params.email)) {
            throw Throwable("用户已存在")
        } else {
            val insertedUser = userDao.inert(params)
            if (insertedUser == null) {
                throw Throwable("新增用户失败")
            } else {
                Result.success(
                    User(
                        id = insertedUser.id,
                        email = insertedUser.email,
                    )
                )
            }
        }
       return result.send()
    }

    override suspend fun signIn(params: SignParams): ActionResult<User> {
        val user = userDao.findByEmail(params.email)
        val result = if (user == null) {
            throw Throwable("当前账号未注册！")
        } else {
            if (user.password == hashPassword(params.password)) {
                Result.success(
                    User(
                        id = user.id,
                        email = user.email,
                    )
                )
            } else {
                throw Throwable("密码有误，请检查")
            }
        }
        return result.send()
    }

    private suspend fun userAlreadyExist(email: String): Boolean {
        return userDao.findByEmail(email) != null
    }
}