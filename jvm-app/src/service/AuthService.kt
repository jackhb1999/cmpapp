package service

import com.hb.model.AuthResponse
import com.hb.model.SignInParams
import com.hb.model.SignUpParams
import common.data.KtorApi
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

internal class AuthService : KtorApi() {
    suspend fun signUp(request: SignUpParams): AuthResponse = client.post {
        endPoint(path = "signup")
        setBody(request)
    }.body()

    suspend fun signIn(request: SignInParams): AuthResponse = client.post {
        endPoint(path = "signin")
        setBody(request)
    }.body()

}
