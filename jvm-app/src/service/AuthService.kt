package service

import com.hb.model.AuthResponse
import com.hb.model.SignInParams
import com.hb.model.SignUpParams
import common.data.KtorApi
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

internal class AuthService : KtorApi() {
    suspend fun signUp(request: SignUpParams): AuthResponse = client.post {
        endPoint(path = "signup")
        setBody(request)
        contentType(ContentType.Application.Json) // 告诉服务器：我发送的是JSON
        accept(ContentType.Application.Json)
    }.body()

    suspend fun signIn(request: SignInParams): AuthResponse = client.post {
        endPoint(path = "login")
        setBody(request)
    }.body()

}
