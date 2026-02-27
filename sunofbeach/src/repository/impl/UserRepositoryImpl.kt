package repository.impl

import kotlinx.coroutines.withContext
import model.LoginParam
import repository.UserRepository
import service.ApiService
import util.DispatcherProvider

internal class UserRepositoryImpl(
    private val dispatcher: DispatcherProvider,
    private val apiService: ApiService
) : UserRepository {
    override suspend fun login(captcha: String, params: LoginParam): String? {
        return withContext(dispatcher.io) {
            try {
                apiService.login(captcha, params)
            } catch (e: Exception) {
                e.message
            }
        }
    }

//    override suspend fun signIn(params: SignInParams): Result<AuthResponse> {
//        return withContext(dispatcher.io) {
//            try {
//                val response = apiService.signIn(params)
//                if (response.data == null) {
//                    Result.Error(message = response.errorMessage)
//                } else {
//                    Result.Success(response)
//                }
//            } catch (e: Exception) {
//                Result.Error(message = e.message)
//            }
//        }
//    }

}