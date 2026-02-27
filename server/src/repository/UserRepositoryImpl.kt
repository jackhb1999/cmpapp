package com.hb.repository

import com.hb.dao.user.UserDao
import model.AuthResponse
import model.UserSettingsData
import model.SignInParams
import model.SignUpParams
import com.hb.plugins.generateJWTToken
import com.hb.security.hashPassword
import util.Result
import io.ktor.http.*
import repository.UserRepository

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun signUp(params: SignUpParams): Result<AuthResponse> {
        return if (userAlreadyExist(params.email)) {
            Result.Error(
                code = HttpStatusCode.Conflict, data = AuthResponse(
                    errorMessage = "User already exist"
                )
            )
        } else {
            val insertedUser = userDao.inert(params)
            if (insertedUser == null) {
                Result.Error(
                    code = HttpStatusCode.InternalServerError, data = AuthResponse(
                        errorMessage = "Error while inserting user"
                    )
                )
            } else {
                Result.Success(
                    data = AuthResponse(
                        data = UserSettingsData(
                            id = insertedUser.id,
                            name = insertedUser.name,
                            bio = insertedUser.bio,
                            avatar = insertedUser.avatar,
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
            Result.Error(
                code = HttpStatusCode.NotFound, data = AuthResponse(
                    errorMessage = "User not found"
                )
            )
        } else {
            if (user.password == hashPassword(params.password)) {
                Result.Success(
                    data = AuthResponse(
                        data = UserSettingsData(
                            id = user.id,
                            name = user.name,
                            bio = user.bio,
                            avatar = user.avatar,
                            token = generateJWTToken(user.email),
                        )
                    )
                )
            } else {
                Result.Error(
                    code = HttpStatusCode.Unauthorized, data = AuthResponse(
                        errorMessage = "Invalid password"
                    )
                )
            }
        }
    }


    private suspend fun userAlreadyExist(email: String): Boolean {
        return userDao.findByEmail(email) != null
    }
}