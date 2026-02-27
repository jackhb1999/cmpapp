package repository

import model.AuthResponse
import model.SignInParams
import model.SignUpParams
import util.Result

interface UserRepository {
    suspend fun signUp(params: SignUpParams): Result<AuthResponse>
    suspend fun signIn(params: SignInParams): Result<AuthResponse>
}