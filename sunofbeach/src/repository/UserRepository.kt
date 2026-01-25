package repository

import com.hb.model.AuthResponse
import model.LoginParam
import util.Result

interface UserRepository {
    suspend fun login(captcha:String,params: LoginParam): String?
//    suspend fun register(params: SignInParams): Result<AuthResponse>
}