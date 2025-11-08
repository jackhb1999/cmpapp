package repository

import com.hb.model.AuthResponse
import com.hb.model.SignInParams
import com.hb.model.SignUpParams
import kotlinx.coroutines.withContext
import service.AuthService
import util.DispatcherProvider
import util.Result

internal class AuthRepositoryImpl(
    private val dispatcher: DispatcherProvider,
    private val authService: AuthService
) : UserRepository {
    override suspend fun signUp(params: SignUpParams): Result<AuthResponse> {
        return withContext(dispatcher.io) {
            try {
                val response = authService.signUp(params)
                if (response.data == null) {
                    Result.Error(message = response.errorMessage)
                } else {
                    Result.Success(response)
                }
            } catch (e: Exception) {
                Result.Error(message = e.message)
            }
        }
    }

    override suspend fun signIn(params: SignInParams): Result<AuthResponse> {
        return withContext(dispatcher.io) {
            try {
                val response = authService.signIn(params)
                if (response.data == null) {
                    Result.Error(message = response.errorMessage)
                } else {
                    Result.Success(response)
                }
            } catch (e: Exception) {
                Result.Error(message = e.message)
            }
        }
    }

}