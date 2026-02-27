package repository

import model.LoginParam

interface UserRepository {
    suspend fun login(captcha:String,params: LoginParam): String?
//    suspend fun register(params: SignInParams): Result<AuthResponse>
}