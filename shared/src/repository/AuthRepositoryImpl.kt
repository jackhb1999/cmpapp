package repository

import data.UserPreferences
import model.AuthResponse
import model.SignInParams
import model.SignUpParams
import kotlinx.coroutines.withContext
import service.AuthService
import util.DispatcherProvider

internal class AuthRepositoryImpl(
    private val dispatcher: DispatcherProvider,
    private val authService: AuthService,
    private val userPreferences: UserPreferences
) : UserRepository {
    override suspend fun signUp(params: SignUpParams): Result<AuthResponse> {
        return withContext(dispatcher.io) {
            val response = authService.signUp(params)
            val result = response.toResult()
            if (result.isSuccess) {
                result.map {
                    userPreferences.setUserData(it.data!!)
                }
            }
            result
        }
    }

    override suspend fun signIn(params: SignInParams): Result<AuthResponse> {
        return withContext(dispatcher.io) {
            val response = authService.signIn(params)
            val result = response.toResult()
            if (result.isSuccess) {
                result.map {
                    userPreferences.setUserData(it.data!!)
                }
            }
            result
        }
    }

}