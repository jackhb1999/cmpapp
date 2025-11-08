package repository

import com.hb.model.AuthResponse
import com.hb.model.SignInParams
import com.hb.model.SignUpParams
import util.Result

interface UserRepository {
    suspend fun signUp(params: SignUpParams): Result<AuthResponse>
    suspend fun signIn(params: SignInParams): Result<AuthResponse>
}